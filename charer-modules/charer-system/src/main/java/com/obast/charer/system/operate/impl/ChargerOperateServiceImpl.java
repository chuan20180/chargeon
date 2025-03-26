package com.obast.charer.system.operate.impl;

import com.obast.charer.api.PriceConfigBo;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.*;
import com.obast.charer.enums.*;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.customer.CustomerLogin;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.order.Orders;
import com.obast.charer.model.park.Park;
import com.obast.charer.model.price.Price;
import com.obast.charer.model.station.Station;
import com.obast.charer.plugin.feign.IRemoteChargerService;
import com.obast.charer.system.operate.IChargerOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩操作服务实现
 */
@Slf4j
@Service
public class ChargerOperateServiceImpl implements IChargerOperateService {


    @Autowired
    private ICustomerLoginData customerLoginData;


    @Autowired
    private IOrdersData ordersData;

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private IChargerGunData chargerGunData;

    @Autowired
    private IPriceData priceData;

    @Autowired
    private IParkData parkData;

    @Autowired
    private IRemoteChargerService remoteChargerService;

    @Override
    public Orders startCharge(ChargePayTypeEnum chargePayType, ChargeStartTypeEnum chargeStartType, ChargeStopTypeEnum chargeStopType, String chargerDn, String chargerGunNo, String customerId, PlatformTypeEnum platform) {

        if(chargePayType == null) {
            throw new BizException(ErrCode.ORDER_CHARGE_PAY_TYPE_NULL);
        }

        if(chargeStartType == null) {
            throw new BizException(ErrCode.ORDER_CHARGE_START_TYPE_NULL);
        }

        if(chargeStopType == null) {
            throw new BizException(ErrCode.ORDER_CHARGE_STOP_TYPE_NULL);
        }

        Charger charger = chargerData.findByDn(chargerDn);
        if(charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        ChargerGun chargerGun = chargerGunData.findByChargerIdAndGunNo(charger.getId(), chargerGunNo);
        if(chargerGun == null) {
            throw new BizException(ErrCode.CHARGER_GUN_NOT_FOUND);
        }

        Customer customer = customerData.findById(customerId);
        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        Station station = stationData.findById(charger.getStationId());
        if(station == null) {
            throw new BizException(ErrCode.STATION_NOT_FOUND);
        }

        Price price = priceData.findById(charger.getPriceId());
        if(price == null) {
            throw new BizException(ErrCode.PRICE_NOT_FOUND);
        }

        BigDecimal balance = customer.getBalanceAmount().subtract(charger.getLowBalance());

        if(balance.compareTo(new BigDecimal(0)) < 0) {
            throw new BizException(ErrCode.CUSTOMER_BALANCE_NOT_ENOUGH);
        }

        String customerLoginId = null;

        if(platform != null) {
            CustomerLogin customerLogin = customerLoginData.findByCustomerIdAndPlatform(customer.getId(), platform);
            if(customerLogin != null) {
                customerLoginId = customerLogin.getId();
            }
        }

        //生成订单
        Orders orders = new Orders();
        orders.setChargePayType(chargePayType);
        orders.setChargeStartType(chargeStartType);
        orders.setChargeStopType(chargeStopType);
        orders.setState(OrderStateEnum.Pending);
        orders.setDeal(OrderDealEnum.NoDeal);
        orders.setCustomerId(customer.getId());
        orders.setUserName(customer.getUserName());
        orders.setChargerName(charger.getName());
        orders.setPriceId(price.getId());
        orders.setPriceProperties(price.getProperties());
        orders.setChargerDn(charger.getDn());
        orders.setGunNo(chargerGun.getNo());
        orders.setStationId(station.getId());
        orders.setStationName(station.getName());
        orders.setStationAddress(station.getAddress());
        orders.setParkAmount(new BigDecimal(0));
        orders.setTenantId(station.getTenantId());
        orders.setAgentId(station.getAgentId());
        orders.setCustomerLoginId(customerLoginId);
        orders.setStartTime(new Date());
        orders.setChargeMinute((short) 0);
        orders.setTotalQty(new BigDecimal(0));
        orders.setTotalQuantity(new BigDecimal(0));
        orders.setTotalAmount(new BigDecimal(0));

        orders = ordersData.add(orders);

        //立即启动
        if(chargeStartType.equals(ChargeStartTypeEnum.Now)) {
            //执行指令
            Response<?> response = remoteChargerService.startCharge(charger.getDn(), chargerGun.getNo(), orders.getTranId(), customer.getLogicalCardNo(), customer.getPhysicalCardNo(),balance);

            log.info("执行结果: {}", response);
            if(response.getCode() != 200) {
                throw new BizException(ErrCode.CHARGER_START_FAIL, response.getMessage());
            }
        }

        return orders;
    }
    /*
     * 结束充电
     */
    @Override
    public void stopCharge(String orderId) {

        Orders orders = ordersData.findById(orderId);
        if(orders == null) {
           throw  new BizException(ErrCode.ORDER_NOT_FOUND);
        }

        Charger charger = chargerData.findByDn(orders.getChargerDn());
        if (charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        ChargerGun chargerGun = chargerGunData.findByChargerIdAndGunNo(charger.getId(), orders.getGunNo());
        if (chargerGun == null) {
            throw new BizException(ErrCode.CHARGER_GUN_NOT_FOUND);
        }

        //执行指令
        Response<?> response = remoteChargerService.stopCharge(charger.getDn(), chargerGun.getNo());

        log.info("执行结果: {}", response);
        if(response.getCode() != 200) {
            throw new BizException(ErrCode.CHARGER_STOP_FAIL, response.getMessage());
        }
    }

    /**
     * 余额更新
     */
    @Override
    public void balanceUpdate(String chargerId, String chargerGunNo, String customerId) {

        Charger charger = chargerData.findById(chargerId);
        if(charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        ChargerGun chargerGun = chargerGunData.findByChargerIdAndGunNo(charger.getId(), chargerGunNo);
        if(chargerGun == null) {
            throw new BizException(ErrCode.CHARGER_GUN_NOT_FOUND);
        }

        Customer customer = customerData.findById(customerId);
        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        BigDecimal balance = customer.getBalanceAmount().subtract(charger.getLowBalance());

        if(balance.compareTo(new BigDecimal(0)) < 0) {
            throw new BizException(ErrCode.CUSTOMER_BALANCE_NOT_ENOUGH);
        }

        //执行指令
        Response<?> response = remoteChargerService.balanceUpdate(charger.getDn(), chargerGun.getNo(), customer.getPhysicalCardNo(), balance);

        log.info("执行结果: {}", response);
        if(response.getCode() != 200) {
            throw new BizException(ErrCode.CHARGER_STOP_FAIL, response.getMessage());
        }
    }

    /**
     * 校时
     */
    @Override
    public void timingConfig(String chargerId) {
        Charger charger = chargerData.findById(chargerId);
        if(charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        //执行指令
        Response<?> response = remoteChargerService.timingConfig(charger.getDn());
        log.info("执行结果: {}", response);
        if(response.getCode() != 200) {
            throw new BizException(ErrCode.CHARGER_STOP_FAIL, response.getMessage());
        }
    }


    /**
     * 参数配置
     */
    @Override
    public void paramConfig(String chargerId, short status, short maxPower) {

        Charger charger = chargerData.findById(chargerId);
        if(charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        //执行指令
        Response<?> response = remoteChargerService.paramConfig(charger.getDn(), status, maxPower);
        log.info("执行结果: {}", response);
        if(response.getCode() != 200) {
            throw new BizException(ErrCode.CHARGER_STOP_FAIL, response.getMessage());
        }
    }

    /**
     * 计价模型下发
     */
    @Override
    public void priceConfig(String chargerId, Price price) {

        Charger charger = chargerData.findById(chargerId);
        if(charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        if(charger.getStatus().equals(EnableStatusEnum.Disabled)) {
            return;
        }

        if(!charger.getOnline().equals(OnlineStatusEnum.Online)) {
            return;
        }

        //执行指令
        Response<?> response = remoteChargerService.priceConfig(new PriceConfigBo(charger.getDn(), price));
        log.info("执行结果: {}", response);
        if(response.getCode() != 200) {
            throw new BizException(ErrCode.CHARGER_STOP_FAIL, response.getMessage());
        }
    }

    /**
     * 重起
     */
    @Override
    public void reboot(String chargerId) {

        Charger charger = chargerData.findById(chargerId);
        if(charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        //执行指令
        Response<?> response = remoteChargerService.reboot(charger.getDn());
        log.info("执行结果: {}", response);
        if(response.getCode() != 200) {
            throw new BizException(ErrCode.CHARGER_STOP_FAIL, response.getMessage());
        }
    }

    /**
     * 二维码下发
     */
    @Override
    public void qrcodeConfig(String chargerId) {

        Charger charger = chargerData.findById(chargerId);
        if(charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        //执行指令
        Response<?> response = remoteChargerService.qrcodeConfig(charger.getDn());
        log.info("执行结果: {}", response);
        if(response.getCode() != 200) {
            throw new BizException(ErrCode.CHARGER_STOP_FAIL, response.getMessage());
        }
    }
}
