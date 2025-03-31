package com.obast.charer.openapi.service.impl;

import com.obast.charer.api.system.feign.IRemoteOperateService;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.*;
import com.obast.charer.enums.*;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.order.Orders;
import com.obast.charer.openapi.config.request.RequestLocale;
import com.obast.charer.openapi.config.request.RequestLocaleHolder;
import com.obast.charer.openapi.dto.bo.BalanceStartChargeBo;
import com.obast.charer.openapi.dto.bo.InstantStartChargeBo;
import com.obast.charer.openapi.dto.bo.StopChargeBo;
import com.obast.charer.openapi.dto.vo.OpenChargerGunVo;
import com.obast.charer.openapi.service.IOpenChargerGunService;
import com.obast.charer.openapi.service.IOpenSysConfigService;
import com.obast.charer.qo.ChargerGunQueryBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Slf4j
@Service
public class OpenChargerGunServiceImpl implements IOpenChargerGunService {

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private IOrdersData ordersData;

    @Autowired
    private IChargerGunData chargerGunData;

    @Autowired
    IOpenSysConfigService sysConfigService;


    @Autowired
    IRemoteOperateService remoteOperateService;


    @Override
    public Paging<OpenChargerGunVo> queryPage(PageRequest<ChargerGunQueryBo> pageRequest) {
        Paging<ChargerGun> pageList = chargerGunData.findPage(pageRequest);
        Paging<OpenChargerGunVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(ChargerGun station: pageList.getRows()) {
            newPageList.getRows().add(fillData(station));
        }
        return newPageList;
    }

    @Override
    public OpenChargerGunVo queryDetail(String id) {
        ChargerGun charger = chargerGunData.findById(id);
        return fillData(charger);
    }

    @Override
    public OpenChargerGunVo queryDetailByChargerIdAndNo(String chargerId, String no) {
        ChargerGun charger = chargerGunData.findByChargerIdAndGunNo(chargerId, no);
        return fillData(charger);
    }

    private OpenChargerGunVo fillData(ChargerGun station) {
        return MapstructUtils.convert(station, OpenChargerGunVo.class);
    }

    @Override
    public ActionResult<?> startCharge(BalanceStartChargeBo bo) {

        ChargePayTypeEnum chargePayType = ChargePayTypeEnum.Balance;

        if(bo.getChargeStartType() == null) {
            throw new BizException(ErrCode.ORDER_CHARGE_START_TYPE_NULL);
        }

        if(bo.getChargeStopType() == null) {
            throw new BizException(ErrCode.ORDER_CHARGE_STOP_TYPE_NULL);
        }

        ChargerGun chargerGun = chargerGunData.findById(bo.getGunId());
        if(chargerGun == null) {
            throw new BizException(ErrCode.CHARGER_GUN_NOT_FOUND);
        }

        Charger charger = chargerData.findById(chargerGun.getChargerId());
        if(charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        //离线检查
        if(!charger.getOnline().equals(OnlineStatusEnum.Online)) {
            throw new BizException(ErrCode.CHARGER_STATE_OFFLINE);
        }

        //空闲检查
        if(!chargerGun.getState().equals(ChargerGunStateEnum.Idle)) {
            throw new BizException(ErrCode.CHARGER_STATE_FAIL);
        }

        //是否插枪检查
        if(chargerGun.getSlot() == 0) {
            throw new BizException(ErrCode.CHARGER_GUN_NOT_SLOT);
        }

        LoginUser loginUser = LoginHelper.getLoginUser();

        Customer customer = customerData.findById(loginUser.getUserId());
        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        //余额检查
        BigDecimal lowBalance = charger.getLowBalance();
        if(lowBalance == null) {
            lowBalance = new BigDecimal(0);
        }

        BigDecimal balanceLimit = new BigDecimal(5);
        try {
            balanceLimit = (BigDecimal) sysConfigService.selectByConfigKey("app.charge.balance.limit");
        } catch (Exception e) {
            log.error("[启动充电]获取启动充电帐户最低余额失败,采用默认值5元({})", e.getLocalizedMessage());
        }

        BigDecimal balance = customer.getBalanceAmount();
        if(balance == null || balance.compareTo(balanceLimit) < 0) {
            throw new BizException(ErrCode.CUSTOMER_BALANCE_NOT_ENOUGH);
        }

        if(lowBalance.compareTo(balanceLimit) >= 0) {
            throw new BizException(ErrCode.SYS_CONFIG_BALANCE_LIMIT_ERROR);
        }

        PlatformTypeEnum platform = null;
        RequestLocale locale = RequestLocaleHolder.getLocale();
        if(locale != null) {
            platform = locale.getPlatform();
        }

        Response<?> result = remoteOperateService.startCharge(chargePayType, bo.getChargeStartType(), bo.getChargeStopType(), charger.getDn(), chargerGun.getNo(), customer.getId(), platform);

        if(result.getCode() == 200) {
            return ActionResult.success(result.getData());
        }

        return ActionResult.fail(result.getMessage());
    }


    @Override
    public ActionResult<?> stopCharge(StopChargeBo bo) {
        Orders order = ordersData.findById(bo.getOrderId());
        if(order == null) {
            throw new BizException(ErrCode.ORDER_NOT_FOUND);
        }
        LoginUser loginUser = LoginHelper.getLoginUser();

        Customer customer = customerData.findById(loginUser.getUserId());

        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        if(!customer.getUserName().equals(order.getUserName())) {
            throw new BizException(ErrCode.INVALID_OPERATE);
        }

        Response<?> result = remoteOperateService.stopCharge(order.getId());

        if(result.getCode() == 200) {
            for(int i = 0; i < 10; i++) {
                log.info("[设备调试]订单状态结果查询 第{}次", i);
                Orders checkOrders = ordersData.findById(order.getId());
                if(checkOrders.getState().equals(OrderStateEnum.Settled)) {
                    return ActionResult.success(checkOrders);
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    return new ActionResult<>(ErrCode.CHARGER_DIRECTIVE_EXECUTE_FAIL.getKey(), "连接设备超时");
                }

            }
            return new ActionResult<>(ErrCode.CHARGER_DIRECTIVE_EXECUTE_FAIL.getKey(), "连接设备超时");

        }

        return ActionResult.fail(result.getMessage());
    }
}
