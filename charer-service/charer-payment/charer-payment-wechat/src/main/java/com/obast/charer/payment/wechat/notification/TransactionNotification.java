package com.obast.charer.payment.wechat.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wechat.pay.java.service.payments.model.PromotionDetail;
import com.wechat.pay.java.service.payments.model.TransactionAmount;
import com.wechat.pay.java.service.payments.model.TransactionPayer;
import lombok.Data;

import java.util.List;

@Data
public class TransactionNotification {
  /** amount */
  @JsonProperty("amount")
  private TransactionAmount amount;
  /** appid */
  @JsonProperty("appid")
  private String appid;
  /** attach */
  @JsonProperty("attach")
  private String attach;
  /** bankType */
  @JsonProperty("bank_type")
  private String bankType;
  /** mchid */
  @JsonProperty("mchid")
  private String mchid;
  /** outTradeNo */
  @JsonProperty("out_trade_no")
  private String outTradeNo;
  /** payer */
  @JsonProperty("payer")
  private TransactionPayer payer;
  /** promotionDetail */
  @JsonProperty("promotion_detail")
  private List<PromotionDetail> promotionDetail;
  /** successTime */
  @JsonProperty("success_time")
  private String successTime;
  /** tradeState */
  public enum TradeStateEnum {
    @JsonProperty("SUCCESS")
    SUCCESS,

    @JsonProperty("REFUND")
    REFUND,

    @JsonProperty("NOTPAY")
    NOTPAY,

    @JsonProperty("CLOSED")
    CLOSED,

    @JsonProperty("REVOKED")
    REVOKED,

    @JsonProperty("USERPAYING")
    USERPAYING,

    @JsonProperty("PAYERROR")
    PAYERROR,

    @JsonProperty("ACCEPT")
    ACCEPT
  }

  @JsonProperty("trade_state")
  private TradeStateEnum tradeState;
  /** tradeStateDesc */
  @JsonProperty("trade_state_desc")
  private String tradeStateDesc;
  /** tradeType */
  public enum TradeTypeEnum {
    @JsonProperty("JSAPI")
    JSAPI,

    @JsonProperty("NATIVE")
    NATIVE,

    @JsonProperty("APP")
    APP,

    @JsonProperty("MICROPAY")
    MICROPAY,

    @JsonProperty("MWEB")
    MWEB,

    @JsonProperty("FACEPAY")
    FACEPAY
  }

  @JsonProperty("trade_type")
  private TradeTypeEnum tradeType;
  /** transactionId */
  @JsonProperty("transaction_id")
  private String transactionId;


}
