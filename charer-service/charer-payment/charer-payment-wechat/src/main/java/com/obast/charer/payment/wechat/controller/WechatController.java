package com.obast.charer.payment.wechat.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.data.platform.IPaymentData;
import com.obast.charer.enums.PaymentIdentifierEnum;
import com.obast.charer.model.payment.Payment;
import com.obast.charer.payment.core.config.PaymentConfig;
import com.obast.charer.payment.core.config.RefundConfig;
import com.obast.charer.payment.wechat.WechatPaymentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：微信支付回调控制器
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@SaIgnore
@RequestMapping("/openapi/wechat")
public class WechatController extends BaseController {

  @Autowired
  private IPaymentData paymentData;

  @Autowired
  private WechatPaymentService wechatPaymentService;

  @SaIgnore
  @ApiOperation("微信支付通知")
  @PostMapping("/payment/notify")
  public void wechatNotify() {
    Payment payment = paymentData.findByIdentifier(PaymentIdentifierEnum.Wechat);
    if(payment == null) {
      throw new BizException(ErrCode.PAYMENT_CONFIG_NOT_FOUND);
    }
    PaymentConfig paymentConfig = new PaymentConfig();
    paymentConfig.setProperties(payment.getProperties());
    wechatPaymentService.paymentNotify(paymentConfig);
  }

  @SaIgnore
  @ApiOperation("微信退款通知")
  @PostMapping("/refund/notify")
  public void refundNotify() {

    Payment payment = paymentData.findByIdentifier(PaymentIdentifierEnum.Wechat);
    if(payment == null) {
      throw new BizException(ErrCode.PAYMENT_CONFIG_NOT_FOUND);
    }

    RefundConfig refundConfig = new RefundConfig();
    refundConfig.setProperties(payment.getProperties());
    wechatPaymentService.refundNotify(refundConfig);
  }
}
