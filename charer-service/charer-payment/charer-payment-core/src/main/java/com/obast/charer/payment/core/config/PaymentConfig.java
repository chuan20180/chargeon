package com.obast.charer.payment.core.config;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ Author：chuan
 * @ Date：2024-10-30-15:24
 * @ Version：1.0
 * @ Description：PyunConfig
 */
@Data
public class PaymentConfig {

    private String properties;

    private String outTradeNo;
    private String subject;

    private BigDecimal amount;

    private String payer;

}
