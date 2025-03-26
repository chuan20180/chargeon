package com.obast.charer.payment.wechat;

import com.alibaba.fastjson.JSONObject;
import com.obast.charer.api.system.feign.IRemoteRefundService;
import com.obast.charer.api.system.feign.IRemoteTopupService;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.PaymentNotify;
import com.obast.charer.common.model.RefundNotify;
import com.obast.charer.common.properties.CharerProperties;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.enums.PaymentIdentifierEnum;
import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.enums.TopupStateEnum;
import com.obast.charer.payment.core.PaymentResult;
import com.obast.charer.payment.core.RefundResult;
import com.obast.charer.payment.core.config.PaymentConfig;
import com.obast.charer.payment.core.config.RefundConfig;
import com.obast.charer.payment.core.service.IPaymentService;
import com.obast.charer.payment.wechat.notification.RefundNotification;
import com.obast.charer.payment.wechat.notification.TransactionNotification;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @ Author：chuan
 * @ Date：2024-10-30-11:32
 * @ Version：1.0
 * @ Description：WechatPaymentService
 */
@Slf4j
@Service
public class WechatPaymentService implements IPaymentService {

    @Autowired
    CharerProperties charerProperties;

    @Autowired
    IRemoteTopupService remoteTopupService;

    @Autowired
    IRemoteRefundService remoteRefundService;

    @Override
    public PaymentIdentifierEnum getCode() {
        return PaymentIdentifierEnum.Wechat;
    }

    private WechatProperties parseConfig(String properties) {
        try {
            return JSONObject.parseObject(properties, WechatProperties.class);
        } catch (Exception e) {
            throw new BizException(ErrCode.PARSE_JSON_EXCEPTION);
        }
    }

    @Override
    public PaymentResult prepay(PaymentConfig config) {
        log.debug("[微信支付]prepay开始, 微信支付配置={}", config);
        WechatProperties wechatProperties = parseConfig(config.getProperties());
        String privateKey = wechatProperties.getPrivateKey();
        log.debug("[微信支付]prepay privateKey: \n{}", privateKey);
        String notifyUrl = String.format("%s%s", charerProperties.getOpenapi().getBaseUrl() , "/wechat/payment/notify");
        log.debug("[微信支付]prepay notify_url: {}", notifyUrl);

        RSAAutoCertificateConfig rsaAutoCertificateConfig = new RSAAutoCertificateConfig.Builder()
                .merchantId(wechatProperties.getMerchantId())
                .privateKey(privateKey)
                .merchantSerialNumber(wechatProperties.getMerchantSerialNumber())
                .apiV3Key(wechatProperties.getApiV3Key())
                .build();

        //请求微信支付相关配置
        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(rsaAutoCertificateConfig).signType("RSA").build();

        PrepayRequest request = new PrepayRequest();
        request.setAppid(wechatProperties.getAppId());
        request.setMchid(wechatProperties.getMerchantId());
        request.setDescription("会员充值");
        request.setOutTradeNo(config.getOutTradeNo());
        request.setNotifyUrl(notifyUrl);

        Payer payer = new Payer();
        payer.setOpenid(config.getPayer());
        request.setPayer(payer);

        Amount amount = new Amount();
        amount.setTotal(config.getAmount().multiply(new BigDecimal("100")).intValue());
        request.setAmount(amount);

        log.debug("[微信支付]prepay 预支付请求参数：{}", JsonUtils.toJsonString(request));

        // 调用预下单接口
        PrepayWithRequestPaymentResponse response;
        try {
             response = service.prepayWithRequestPayment(request);
        } catch (Exception e) {
            log.error("[微信支付]prepay发送HTTP请求异常，msg={}", e.getLocalizedMessage());
            throw new BizException(ErrCode.PAYMENT_PREPAY_REQUEST_EXCEPTION);
        }

        if(response  == null) {
            log.error("[微信支付]prepay HTTP返回信息为空");
            throw new BizException(ErrCode.PAYMENT_PREPAY_REQUEST_RETURN_NULL);
        }

        log.info("[微信支付]prepay 返回数据：{}", JsonUtils.toJsonString(response));

        //解析预支付返回信息
        PaymentResult paymentResult;
        try {
            paymentResult = JsonUtils.parseObject(JsonUtils.toJsonString(response), PaymentResult.class);
        } catch (Exception e) {
            log.error("[微信支付]prepay解析返回数据异常，msg={}", e.getLocalizedMessage());
            throw new BizException(ErrCode.PAYMENT_PREPAY_RESPONSE_PARSE_EXCEPTION);
        }

        if(paymentResult  == null) {
            log.error("[微信支付]prepay 解析返回信息为空");
            throw new BizException(ErrCode.PAYMENT_PREPAY_RESPONSE_PARSE_NULL);
        }

        paymentResult.setTranId(config.getOutTradeNo());
        log.info("[微信支付]prepay发起预支付成功，订单【{}】返回信息：{}", config.getOutTradeNo(), paymentResult);

        return paymentResult;
    }


    @Override
    public void paymentNotify(PaymentConfig paymentConfig) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        WechatProperties wechatProperties = parseConfig(paymentConfig.getProperties());
        log.debug("[微信支付通知]收到请求 paymentConfig={}", paymentConfig);

        TransactionNotification transactionResult;
        try {
            BufferedReader br = request.getReader();
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine())!=null) {
                sb.append(str);
            }
            // 构建request，传入必要参数
            NotificationRequest requests = new NotificationRequest.Builder()
                    .withSerialNumber(request.getHeader("Wechatpay-Serial"))
                    .withNonce(request.getHeader("Wechatpay-Nonce"))
                    .withTimestamp(request.getHeader("Wechatpay-Timestamp"))
                    .withSignature(request.getHeader("Wechatpay-Signature"))
                    .withBody(String.valueOf(sb))
                    .build();
            //验签
            NotificationHandler handler = new NotificationHandler(WXPaySignatureCertificateUtil.getVerifier(wechatProperties), wechatProperties.getApiV3Key().getBytes(StandardCharsets.UTF_8));
            //解析请求体
            Notification notification = handler.parse(requests);
            String decryptData = notification.getDecryptData();
            log.debug("[微信支付通知]http返回解密结果: decryptData={}", decryptData);
            transactionResult = JsonUtils.parse(decryptData, TransactionNotification.class);
        }catch (Exception e) {
            log.error("[微信支付通知]解析http返回信息异常 {}", e.getLocalizedMessage());
            throw new BizException(ErrCode.PAYMENT_WECHAT_NOTIFY_PARSE_EXCEPTION);
        }

        if(transactionResult == null) {
            log.error("[微信支付通知]http返回为空");
            throw new BizException(ErrCode.PAYMENT_WECHAT_NOTIFY_RESPONSE_NULL);
        }

        log.debug("[微信支付通知]http返回解析结果: Transaction={}", transactionResult);

        PaymentNotify paymentNotify = new PaymentNotify();
        paymentNotify.setBankType(transactionResult.getBankType());
        paymentNotify.setTransactionId(transactionResult.getTransactionId());
        paymentNotify.setOutTradeNo(transactionResult.getOutTradeNo());
        paymentNotify.setAttach(transactionResult.getAttach());

        BigDecimal amount;
        try {
            amount = new BigDecimal(transactionResult.getAmount().getTotal()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.error("[微信支付通知]解析支付金额异常 msg={}", e.getLocalizedMessage());
            throw new BizException(ErrCode.PAYMENT_WECHAT_NOTIFY_PARSE_AMOUNT_EXCEPTION);
        }
        paymentNotify.setAmount(amount);

        String payerOpenId;
        try {
            payerOpenId = transactionResult.getPayer().getOpenid();
        } catch (Exception e) {
            log.error("[微信支付通知]解析openID异常 msg={}", e.getLocalizedMessage());
            throw new BizException(ErrCode.PAYMENT_WECHAT_NOTIFY_PARSE_OPENID_EXCEPTION);
        }
        paymentNotify.setPayerOpenId(payerOpenId);

        TopupStateEnum topupStateEnum;

        switch (transactionResult.getTradeState()) {
            case SUCCESS:
                topupStateEnum = TopupStateEnum.Successful;
                break;
            case REFUND:
            case NOTPAY:
            case CLOSED:
                topupStateEnum = TopupStateEnum.Fail;
                break;
            default:
                log.error("[微信支付通知]解析支付状态异常");
                throw new BizException(ErrCode.PAYMENT_WECHAT_NOTIFY_PARSE_STATUS_EXCEPTION);
        }
        paymentNotify.setTradeState(topupStateEnum.name());

        log.debug("[微信支付通知]开始发送到system进行入帐， topupTranId={}", paymentNotify.getOutTradeNo());
        remoteTopupService.complete(paymentNotify);
    }

    @Override
    public RefundResult refund(RefundConfig config) {
        WechatProperties wechatProperties = parseConfig(config.getProperties());

        log.debug("[微信退款]退款开始, 退款配置={}", config);

        String privateKey = wechatProperties.getPrivateKey();
        RSAAutoCertificateConfig rsaAutoCertificateConfig = new RSAAutoCertificateConfig.Builder()
                .merchantId(wechatProperties.getMerchantId())
                .privateKey(privateKey)
                .merchantSerialNumber(wechatProperties.getMerchantSerialNumber())
                .apiV3Key(wechatProperties.getApiV3Key())
                .build();

        String notifyUrl = String.format("%s%s", charerProperties.getOpenapi().getBaseUrl() , "/wechat/refund/notify");
        log.debug("[微信退款] notify_url: {}", notifyUrl);


        RefundService service = new RefundService.Builder().config(rsaAutoCertificateConfig).build();
        Refund refundResponse = null;
        try {
            CreateRequest request = new CreateRequest();
            request.setOutRefundNo(config.getOutRefundNo());
            request.setTransactionId(config.getOutTradeNo());
            request.setNotifyUrl(notifyUrl);

            AmountReq amount = new AmountReq();
            amount.setRefund(config.getAmount().multiply(new BigDecimal(100)).longValue());
            amount.setCurrency("CNY");
            amount.setTotal(config.getTotal().multiply(new BigDecimal(100)).longValue());
            request.setAmount(amount);
            log.debug("[微信退款]退款请求开始, request={}", JsonUtils.toJsonString(request));
            refundResponse = service.create(request);
            log.debug("[微信退款]退款请求结果 {}", JsonUtils.toJsonString(refundResponse));
        } catch (HttpException e) {
            log.error("[微信退款]微信退款发送HTTP请求失败1，错误信息：{}", JsonUtils.toJsonString(e.getMessage()));
            throw new BizException(ErrCode.REFUND_WECHAT_REQUEST_EXCEPTION);
        } catch (ServiceException e) {
            log.error("[微信退款]微信退款发送HTTP请求失败2，错误信息：{}", JsonUtils.toJsonString(e.getResponseBody()));
            try {
                WechatResult result = JsonUtils.parse(e.getResponseBody(), WechatResult.class);
                assert result != null;
                throw new BizException(ErrCode.REFUND_WECHAT_SERVICE_EXCEPTION, result.getMessage());
            } catch (Exception ignored) {}
        } catch (MalformedMessageException e) { // 服务返回成功，返回体类型不合法，或者解析返回体失败
            log.error("[微信退款]微信退款发送HTTP请求失败3，错误信息：{}", JsonUtils.toJsonString(e.getMessage()));
            throw new BizException(ErrCode.REFUND_WECHAT_MESSAGE_EXCEPTION, e.getMessage());
        }

        if(refundResponse == null) {
            log.error("[微信退款]退款回调返回为空");
            throw new BizException(ErrCode.REFUND_WECHAT_RESPONSE_NULL);
        }

        RefundResult refundResult = new RefundResult();
        refundResult.setRefundId(refundResponse.getRefundId());
        refundResult.setOutRefundNo(refundResponse.getOutRefundNo());
        refundResult.setTransactionId(refundResponse.getTransactionId());

        BigDecimal amount;
        try {
            amount = new BigDecimal(refundResponse.getAmount().getRefund()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.error("[微信退款]解析退款金额异常");
            throw new BizException(ErrCode.REFUND_WECHAT_PARSE_AMOUNT_EXCEPTION);
        }
        refundResult.setAmount(amount);

        RefundStateEnum refundStatus;
        switch (refundResponse.getStatus()) {
            case SUCCESS:
                refundStatus = RefundStateEnum.Successful;
                break;
            case PROCESSING:
                refundStatus = RefundStateEnum.Processing;
                break;
            case CLOSED:
                refundStatus = RefundStateEnum.Closed;
                break;
            case ABNORMAL:
                refundStatus = RefundStateEnum.Abnormal;
                break;
            default:
                log.error("[微信退款]解析退款状态异常");
                throw new BizException(ErrCode.REFUND_WECHAT_PARSE_REFUND_STATUS_EXCEPTION);
        }
        refundResult.setStatus(refundStatus.name());

        log.info("[微信退款]发起退款成功，订单={}, 返回信息={}", config.getOutRefundNo(), refundResult);
        return refundResult;
    }

    @Override
    public void refundNotify(RefundConfig refundConfig) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        WechatProperties wechatProperties = parseConfig(refundConfig.getProperties());
        log.debug("[微信退款通知]收到请求，退款配置参数={}", refundConfig);

        RefundNotification refundNotification = null;

        try {
            BufferedReader br = request.getReader();
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine())!=null) {
                sb.append(str);
            }
            // 构建request，传入必要参数
            NotificationRequest requests = new NotificationRequest.Builder()
                    .withSerialNumber(request.getHeader("Wechatpay-Serial"))
                    .withNonce(request.getHeader("Wechatpay-Nonce"))
                    .withTimestamp(request.getHeader("Wechatpay-Timestamp"))
                    .withSignature(request.getHeader("Wechatpay-Signature"))
                    .withBody(String.valueOf(sb))
                    .build();
            //验签
            NotificationHandler handler = new NotificationHandler(WXPaySignatureCertificateUtil.getVerifier(wechatProperties), wechatProperties.getApiV3Key().getBytes(StandardCharsets.UTF_8));
            //解析请求体
            Notification notification = handler.parse(requests);
            String decryptData = notification.getDecryptData();
            log.debug("[微信退款通知]退款回调返回数据  decryptData={}", decryptData);
            //解析
             refundNotification = JsonUtils.parse(decryptData, RefundNotification.class);
        }catch (Exception e) {
            log.error("[微信退款通知]退款回调解析异常 {}", e.getLocalizedMessage());
            throw new BizException(ErrCode.REFUND_WECHAT_NOTIFY_REQUEST_EXCEPTION, e.getLocalizedMessage());
        }

        if(refundNotification == null) {
            log.error("[微信退款通知]退款回调返回为空");
            throw new BizException(ErrCode.REFUND_WECHAT_NOTIFY_RESPONSE_NULL);
        }

        log.debug("[微信退款通知]退款回调返回结果: {}", refundNotification);

        RefundNotify refundNotify = new RefundNotify();
        refundNotify.setRefundId(refundNotification.getRefundId());
        refundNotify.setOutRefundNo(refundNotification.getOutRefundNo());
        refundNotify.setTransactionId(refundNotification.getTransactionId());
        refundNotify.setOutTradeNo(refundNotification.getOutTradeNo());
        refundNotify.setUserReceivedAccount(refundNotification.getUserReceivedAccount());

        refundNotify.setSuccessTime(new Date());

        BigDecimal amount;
        try {
            amount = new BigDecimal(refundNotification.getAmount().getRefund()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.error("[微信退款通知]解析退款金额异常");
            throw new BizException(ErrCode.REFUND_WECHAT_NOTIFY_PARSE_CREATE_TIME_EXCEPTION);
        }
        refundNotify.setAmount(amount);

        RefundStateEnum refundStatus;

        switch (refundNotification.getRefundStatus()) {
            case SUCCESS:
                refundStatus = RefundStateEnum.Successful;
                break;
            case CLOSED:
                refundStatus = RefundStateEnum.Closed;
                break;
            case ABNORMAL:
                refundStatus = RefundStateEnum.Abnormal;
                break;
            default:
                log.error("[微信退款通知]解析退款状态异常");
                throw new BizException(ErrCode.REFUND_WECHAT_NOTIFY_PARSE_REFUND_STATUS_EXCEPTION);
        }
        refundNotify.setRefundStatus(refundStatus.name());

        log.debug("[微信退款通知]开始发送到system进行出帐， topupTranId={}", refundNotify.getOutTradeNo());
        remoteRefundService.complete(refundNotify);
    }
}
