package com.obast.charer.system.listener;

import com.obast.charer.data.business.IAlertConfigData;
import com.obast.charer.model.order.Orders;
import com.obast.charer.system.listener.event.OrderSettledEvent;
import com.obast.charer.system.operate.IOrdersOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class OrderEventListener {

    @Autowired
    private IOrdersOperateService ordersOperateService;

    //订单结算完成事件
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    //@EventListener
    public void doOrderSettledEvent(OrderSettledEvent event) {
        Orders order = event.getOrder();
        log.info("[事件调试]收到订单结算完成事件, orderId={}", order.getId());

        //开始通知
        ordersOperateService.notify(order.getId());

        //开始结算
        ordersOperateService.deal(order.getId());
    }
}
