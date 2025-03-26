package com.obast.charer.system.operate;

import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.enums.ChargePayTypeEnum;
import com.obast.charer.enums.ChargeStartTypeEnum;
import com.obast.charer.enums.ChargeStopTypeEnum;
import com.obast.charer.model.order.Orders;
import com.obast.charer.model.price.Price;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩管理服务接口
 */
public interface IChargerOperateService {

    Orders startCharge(ChargePayTypeEnum chargePayType, ChargeStartTypeEnum chargeStartType, ChargeStopTypeEnum chargeStopType, String chargerDn, String chargerGunNo, String customerId, PlatformTypeEnum platform) ;

    void stopCharge(String orderId);

    void balanceUpdate(String chargerId, String chargerGunNo, String customerId);

    void timingConfig(String chargerId);

    void paramConfig(String chargerId, short status, short maxPower);

    void priceConfig(String chargerId, Price price);


    void reboot(String chargerId);


    void qrcodeConfig(String chargerId);

}
