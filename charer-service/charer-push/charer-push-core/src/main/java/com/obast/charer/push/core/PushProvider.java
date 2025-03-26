package com.obast.charer.push.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ Author：chuan
 * @ Date：2024-10-30-11:23
 * @ Version：1.0
 * @ Description：PaymentProvider
 */
@Slf4j
@Component
public class PushProvider implements ApplicationContextAware {

    public static final ConcurrentHashMap<String, IPushService> PUSH_BEAN_MAP = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IPushService> map = applicationContext.getBeansOfType(IPushService.class);
        log.debug("推送服务列表: {}", map);
        map.forEach((key, value) -> PUSH_BEAN_MAP.put(value.getIdentifier().name(), value));
    }

    public static <T extends IPushService> T getPushService(String identifier) {
        return (T) PUSH_BEAN_MAP.get(identifier);
    }
}