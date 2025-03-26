package com.obast.charer.system.task;


import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.data.business.ICustomerLoginData;
import com.obast.charer.data.business.IOrdersData;
import com.obast.charer.enums.OrderStateEnum;
import com.obast.charer.model.customer.CustomerLogin;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.order.Orders;
import com.obast.charer.system.operate.IChargerOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 定时启动订单检查
 */
@Slf4j
@Component
public class OrdersTimerTask {

    @Autowired
    private IChargerOperateService chargerOperateService;

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private IOrdersData ordersData;

    @Autowired
    private ICustomerLoginData customerLoginData;

    //等待启动的订单
    @Scheduled(fixedDelay = 10, initialDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void timerOrderStart() {
        List<Orders> orders = ordersData.findWaitStartOrders();
        for (Orders order : orders) {
            if (!order.getState().equals(OrderStateEnum.Pending)) {
                continue;
            }

//            Date instantStartTime = order.getTimerStartTime();
//
//            if(instantStartTime == null) {
//                order.setState(OrderStateEnum.Fail);
//                order.setTimerErrMsg("订单延时启动时间错误");
//                continue;
//            }
//
//            if(instantStartTime.compareTo(new Date()) > 0) {
//                log.debug("[订单定时任务] 开始启动订单 tranId: {}", order.getTranId());
//                Charger charger = chargerData.findByDn(order.getChargerDn());
//                if(charger == null) {
//                    throw new BizException(ErrCode.CHARGER_NOT_FOUND);
//                }
//
//                CustomerLogin customerLogin = customerLoginData.findById(order.getCustomerLoginId());
//                if(customerLogin == null) {
//                    throw new BizException(ErrCode.CUSTOMER_LOGIN_NOT_FOUND);
//                }
//
//                try {
//                    chargerOperateService.startCharge(order.getChargePayType(), order.getChargeStartType(), order.getChargeStopType(), charger.getId(), order.getGunNo(), order.getCustomerId(), customerLogin.getPlatform());
//                } catch (Exception e) {
//                    order.setState(OrderStateEnum.Fail);
//                    order.setTimerErrMsg(e.getLocalizedMessage());
//                }
//            }
        }
    }

    //等待停止的订单
    @Scheduled(fixedDelay = 10, initialDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void timerOrderStop() {

        //log.debug("[订单定时任务] 开始检查待停止的订单");
        List<Orders> orders = ordersData.findWaitStopOrders();
        for (Orders order : orders) {
            if (!order.getState().equals(OrderStateEnum.Processing)) {
                continue;
            }

//            Integer timerChargeMinute = order.getStopChargeMinute();
//
//            if(timerChargeMinute == null || timerChargeMinute <= 0) {
//                order.setState(OrderStateEnum.Fail);
//                order.setTimerErrMsg("订单定制停止时间错误");
//                continue;
//            }
//
//            Date startTime = order.getStartTime();
//            if(startTime == null) {
//                order.setState(OrderStateEnum.Fail);
//                order.setTimerErrMsg("订单启动时间错误");
//                continue;
//            }
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(startTime);
//            calendar.add(Calendar.MINUTE, timerChargeMinute);
//            Date stopTime = calendar.getTime();
//
//            if(stopTime.compareTo(new Date()) > 0) {
//                log.debug("[订单定时任务] 开始停止订单 tranId: {}", order.getTranId());
//                Charger charger = chargerData.findByDn(order.getChargerDn());
//                if(charger == null) {
//                    throw new BizException(ErrCode.CHARGER_NOT_FOUND);
//                }
//
//                CustomerLogin customerLogin = customerLoginData.findById(order.getCustomerLoginId());
//                if(customerLogin == null) {
//                    throw new BizException(ErrCode.CUSTOMER_LOGIN_NOT_FOUND);
//                }
//
//                try {
//                    chargerOperateService.stopCharge(order.getId());
//                } catch (Exception e) {
//                    order.setState(OrderStateEnum.Fail);
//                    order.setTimerErrMsg(e.getLocalizedMessage());
//                }
//            }
        }
    }
}
