package com.obast.charer.plugin.service;

import com.obast.charer.common.api.IMessage;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.order.Orders;
import com.obast.charer.model.price.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：充电订单管理服务接口
 */
public interface IChargerCtrlService {
    void invokeService(String chargerDn, short serial, ChargerDirectiveEnum invoke, IMessage message);

}
