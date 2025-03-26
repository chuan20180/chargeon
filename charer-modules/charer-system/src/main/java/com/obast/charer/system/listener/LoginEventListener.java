package com.obast.charer.system.listener;

import com.obast.charer.common.log.event.LogininforEvent;
import com.obast.charer.model.order.Orders;
import com.obast.charer.system.operate.IOrdersOperateService;
import com.obast.charer.system.service.system.ISysLoginInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginEventListener {

    @Autowired
    private ISysLoginInfoService sysLoginInfoService;

    //登陆成功事件
    @Async
    @EventListener
    public void doLoginSuccessfulEvent(LogininforEvent event) {
        log.info("[事件调试]收到登陆成功事件, event={}", event);
        sysLoginInfoService.recordLoginInfo(event);
    }
}
