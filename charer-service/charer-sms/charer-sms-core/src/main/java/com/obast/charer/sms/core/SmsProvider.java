package com.obast.charer.sms.core;

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
public class SmsProvider implements ApplicationContextAware {

    public static final ConcurrentHashMap<String, ISmsService> SMS_BEAN_MAP = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ISmsService> map = applicationContext.getBeansOfType(ISmsService.class);
        log.debug("短信服务列表: {}", map);
        map.forEach((key, value) -> SMS_BEAN_MAP.put(value.getIdentifier(), value));
    }

    public static <T extends ISmsService> T getSmsService(String identifier) {
        return (T) SMS_BEAN_MAP.get(identifier);
    }
}