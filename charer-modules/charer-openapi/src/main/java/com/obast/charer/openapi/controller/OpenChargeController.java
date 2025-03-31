package com.obast.charer.openapi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.enums.ChargerActionEnum;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.enums.OrderStateEnum;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.openapi.dto.bo.*;
import com.obast.charer.openapi.dto.vo.*;
import com.obast.charer.openapi.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"openapi-充电枪"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/charge")
public class OpenChargeController {

    @Autowired
    private IOpenChargerService openChargerService;

    @Autowired
    private IOpenStationService openStationService;

    @Autowired
    private IOpenPriceService openPriceService;

    @Autowired
    private IOpenChargerGunService openChargerGunService;

    @Autowired
    private IOpenCustomerService openCustomerService;

    @Autowired
    private IOpenOrdersService openOrdersService;

    @Autowired
    private IOpenProductService openProductService;

    @Autowired
    private IOpenProtocolService openProtocolService;

    @Autowired
    private IOpenPromotionService openPromotionService;

    @ApiOperation("扫描二维码检测")
    @PostMapping("/scan")
    public Map<String, Object> scan(@RequestBody Request<ChargeScanBo> request) {

        ChargeScanBo bo = request.getData();

        String customerId = LoginHelper.getUserId();
        OpenCustomerVo openCustomerVo = openCustomerService.queryDetail(customerId);
        if(openCustomerVo == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        ProductTypeEnum productTypeEnum = null;
        try {
             productTypeEnum = ProductTypeEnum.valueOf(bo.getProduct());
        } catch (Exception e) {
            log.error("未识别的产品类型: {}", bo.getProduct());
        }

        if(productTypeEnum == null) {
            throw new BizException(ErrCode.PRODUCT_UNRECOGNIZED_PRODUCT_TYPE);
        }

        String productKey = bo.getProductKey();
        OpenProductVo openProductVo = openProductService.findByProductKey(productKey);
        if(openProductVo == null) {
            throw new BizException(ErrCode.PRODUCT_NOT_FOUND);
        }

        String protocolKey = openProductVo.getProtocolKey();
        OpenProtocolVo openProtocolVo = openProtocolService.findByProtocolKey(protocolKey);
        if(openProtocolVo == null) {
            throw new BizException(ErrCode.PROTOCOL_NOT_FOUND);
        }

        String no = bo.getNo();
        if(StringUtils.isBlank(no)) {
            throw new BizException(ErrCode.CHARGER_DN_INVALID);
        }

        int dnLength = openProtocolVo.getCharger().getDnLength();

        if(no.length() != (dnLength+2)) {
            throw new BizException(ErrCode.CHARGER_DN_INVALID);
        }

        String dn = no.substring(0, dnLength);
        String gunNo = no.substring(dnLength, dnLength+2);

        log.debug("dn: {}, gunNo: {}", dn, gunNo);

        OpenChargerVo openChargerVo = openChargerService.queryDetailByDn(dn);
        if(openChargerVo == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        OpenChargerGunVo openChargerGunVo = openChargerGunService.queryDetailByChargerIdAndNo(openChargerVo.getId(), gunNo);
        if(openChargerGunVo == null) {
            throw new BizException(ErrCode.CHARGER_GUN_NOT_FOUND);
        }


        ChargerActionEnum chargerActionEnum = null;
        try {
            chargerActionEnum = ChargerActionEnum.valueOf(bo.getAction());
        } catch (Exception e) {
            log.error("未识别的Charger Action类型: {}", bo.getAction());
        }

        if(chargerActionEnum == null) {
            throw new BizException(ErrCode.CHARGER_UNRECOGNIZED_CHARGER_ACTION);
        }

        Map<String, Object> result = new HashMap<>();

        //查找当前用户充电中的订单(有正在充电的订单直接返回)
        List<OpenOrdersVo> chargingOrders = openOrdersService.queryByCustomerIdAndState(customerId, OrderStateEnum.Processing);
        if(chargingOrders != null && !chargingOrders.isEmpty()) {
            result.put("type", OrderStateEnum.Processing);
            result.put("orderId", chargingOrders.get(0).getId());
            return result;
        }

        //查找当前用户未结算的订单(有未结算的订单直接返回)
        List<OpenOrdersVo> noSettledOrders = openOrdersService.queryByCustomerIdAndState(customerId, OrderStateEnum.Finished);
        if(noSettledOrders != null && !noSettledOrders.isEmpty()) {
            result.put("type", OrderStateEnum.Finished);
            result.put("orderId", noSettledOrders.get(0).getId());
            return result;
        }

        //其它状态或者没有找到自己的订单
        result.put("type", ChargerGunStateEnum.Idle);
        result.put("dn", openChargerVo.getDn());
        result.put("gunNo", openChargerGunVo.getNo());
        return result;
    }

    @ApiOperation("准备余额充电")
    @PostMapping("/balancePend")
    public Map<String, Object> balancePend(@RequestBody Request<BalancePendBo> request) {
        Map<String, Object> result = new HashMap<>();

        BalancePendBo bo = request.getData();
        OpenChargerVo openChargerVo = openChargerService.queryDetailByDn(bo.getDn());
        if(openChargerVo == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }
        result.put("charger", openChargerVo);

        OpenChargerGunVo openChargerGunVo = openChargerGunService.queryDetailByChargerIdAndNo(openChargerVo.getId(), bo.getGunNo());
        if(openChargerGunVo == null) {
            throw new BizException(ErrCode.CHARGER_GUN_NOT_FOUND);
        }
        result.put("chargerGun", openChargerGunVo);

        OpenStationVo openStationVo = openStationService.queryDetail(openChargerVo.getStationId());
        if(openStationVo == null) {
            throw new BizException(ErrCode.STATION_NOT_FOUND);
        }
        result.put("station", openStationVo);

        OpenPriceVo openPriceVo = openPriceService.queryDetail(openChargerVo.getPriceId());
        if(openPriceVo == null) {
            throw new BizException(ErrCode.PRICE_NOT_FOUND);
        }

        result.put("price", openPriceVo);

        String customerId = LoginHelper.getUserId();
        OpenCustomerVo openCustomerVo = openCustomerService.queryDetail(customerId);
        if(openCustomerVo == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }
        result.put("customer", openCustomerVo);

        List<OpenPromotionVo> promotions = openPromotionService.queryAvailableListById(openStationVo.getId());
        result.put("promotions", promotions);

        return result;
    }

    @ApiOperation("余额充电")
    @PostMapping("/balanceStart")
    public Object balanceStart(@RequestBody @Validated Request<BalanceStartChargeBo> bo) {
        log.debug("余额启动充电 params: {}", bo);
        ActionResult<?> result =  openChargerGunService.startCharge(bo.getData());
        if(result.getCode() > 0) {
            throw new BizException(ErrCode.CHARGER_START_FAIL, result.getReason());
        }
        return result.getData();
    }

    @ApiOperation("停止充电")
    @PostMapping("/stop")
    public Object stop(@RequestBody @Validated Request<StopChargeBo> bo) {
        ActionResult<?> result =  openChargerGunService.stopCharge(bo.getData());
        if(result.getCode() > 0) {
            throw new BizException(ErrCode.CHARGER_STOP_FAIL, result.getReason());
        }
        return result.getData();
    }
}