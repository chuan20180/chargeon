package com.obast.charer.payment.wechat;

import com.obast.charer.payment.core.config.PaymentConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @ Author：chuan
 * @ Date：2024-10-30-15:24
 * @ Version：1.0
 * @ Description：WechatConfig
 */


@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class WechatConfig extends PaymentConfig {


    private String orderId;

    private String subject;

    private BigDecimal amount;

    private String payer;

}
