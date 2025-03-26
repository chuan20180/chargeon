package com.obast.charer.payment.core;

import com.obast.charer.enums.PaymentIdentifierEnum;
import com.obast.charer.payment.core.service.IPaymentService;
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
public class PaymentProvider implements ApplicationContextAware {

    public static final ConcurrentHashMap<PaymentIdentifierEnum, IPaymentService> PAYMENT_BEAN_MAP = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IPaymentService> map = applicationContext.getBeansOfType(IPaymentService.class);
        map.forEach((key, value) -> PAYMENT_BEAN_MAP.put(value.getCode(), value));
    }

    public static <T extends IPaymentService> T getPayment(PaymentIdentifierEnum typeEnum) {
        return (T) PAYMENT_BEAN_MAP.get(typeEnum);
    }
}