package com.obast.charer.payment.core;

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
public class RefundResult {
  private String refundId;

  private String outRefundNo;

  private String transactionId;

  private BigDecimal amount;

  private String status;

}
