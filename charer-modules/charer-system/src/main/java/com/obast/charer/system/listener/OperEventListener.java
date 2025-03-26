package com.obast.charer.system.listener;

import com.obast.charer.common.log.event.LogininforEvent;
import com.obast.charer.common.log.event.OperLogEvent;
import com.obast.charer.system.service.system.ISysLoginInfoService;
import com.obast.charer.system.service.system.ISysOperLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OperEventListener {

    @Autowired
    private ISysOperLogService sysOperLogService;

    //登陆成功事件
    @Async
    @EventListener
    public void doLoginSuccessfulEvent(OperLogEvent event) {
        log.info("[事件调试]收到操作事件, event={}", event);
        sysOperLogService.recordLog(event);
    }
}
