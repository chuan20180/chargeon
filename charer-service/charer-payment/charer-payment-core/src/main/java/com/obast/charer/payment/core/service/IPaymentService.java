package com.obast.charer.payment.core.service;

import com.obast.charer.enums.PaymentIdentifierEnum;
import com.obast.charer.payment.core.PaymentResult;
import com.obast.charer.payment.core.RefundResult;
import com.obast.charer.payment.core.config.PaymentConfig;
import com.obast.charer.payment.core.config.RefundConfig;

/**
 * @ Author：chuan
 * @ Date：2024-10-30-11:24
 * @ Version：1.0
 * @ Description：IPaymentService
 */
public interface IPaymentService {

    PaymentIdentifierEnum getCode();

    PaymentResult prepay(PaymentConfig config);

    RefundResult refund(RefundConfig config);

    void paymentNotify(PaymentConfig config);

    void refundNotify(RefundConfig config);

}
