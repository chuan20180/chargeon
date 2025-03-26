package com.obast.charer.plugin.service;

import com.obast.charer.common.model.ActionResult;
import com.obast.charer.model.price.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：充电订单管理服务接口
 */
public interface IChargerInvokeService {

    ActionResult<?> startCharge(String chargerDn, String chargerGunNo, String orderTranId, String logicalCardNo, String physicalCardNo, BigDecimal balance);

    ActionResult<?> stopCharge(String chargerDn, String chargerGunNo);

    ActionResult<?> timingConfig(String chargerDn);

    ActionResult<?> balanceUpdate(String chargerDn, String chargerGunNo, String physicalCardNo, BigDecimal balance);

    ActionResult<?> paramConfig(String chargerDn, short status, short maxPower);

    ActionResult<?> priceConfig(String chargerDn, Price price);

    ActionResult<?> reboot(String chargerDn);

    ActionResult<?> qrcodeConfig(String chargerDn);

}
