package com.obast.charer.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/** Refund */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentNotify {

  private BigDecimal amount;

  /** attach */
  private String attach;

  /** bankType */
  private String bankType;

  /** outTradeNo */
  private String outTradeNo;

  /** payer */
  private String payerOpenId;

  /** tradeState */
  private String tradeState;
  /** tradeStateDesc */

  private String tradeStateDesc;

  private String tradeType;

  /** transactionId */
  private String transactionId;




}
