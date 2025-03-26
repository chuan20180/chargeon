package com.obast.charer.payment.mbank;

import com.alibaba.fastjson.JSONObject;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.enums.PaymentIdentifierEnum;
import com.obast.charer.payment.core.PaymentResult;
import com.obast.charer.payment.core.RefundResult;
import com.obast.charer.payment.core.config.PaymentConfig;
import com.obast.charer.payment.core.config.RefundConfig;
import com.obast.charer.payment.core.service.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ Author：chuan
 * @ Date：2024-10-30-11:32
 * @ Version：1.0
 * @ Description：MbankService
 */
@Slf4j
@Service
public class MbankService implements IPaymentService {

    @Override
    public PaymentIdentifierEnum getCode() {
        return PaymentIdentifierEnum.MBank;
    }

    private MbankProperties parseConfig(String properties) {
        try {
            return JSONObject.parseObject(properties, MbankProperties.class);
        } catch (Exception e) {
            throw new BizException(ErrCode.PARSE_JSON_EXCEPTION);
        }
    }

    @Override
    public PaymentResult prepay(PaymentConfig config) {
        log.debug("mbank支付配置: {}", config);
        MbankProperties wechatProperties = parseConfig(config.getProperties());
        //https://app.mbank.kg/deeplink?service=5e32b7a-86d5-4830-bae9-ebe22a83a364&account=332712306&amount=5
        String url = String.format("%s?service=%s&account=%s&amount=%s", wechatProperties.getDeeplink(), wechatProperties.getService(), config.getOutTradeNo(), config.getAmount());

        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setDeeplink(url);

        return paymentResult;
    }

    @Override
    public void paymentNotify(PaymentConfig config) {


    }

    @Override
    public RefundResult refund(RefundConfig config) {
        return null;
    }

    @Override
    public void refundNotify(RefundConfig config) {

    }

}
