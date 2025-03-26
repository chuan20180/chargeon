package com.obast.charer.payment.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @ Author：chuan
 * @ Date：2024-10-30-15:24
 * @ Version：1.0
 * @ Description：PyunConfig
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResult {

    private String appId;


    private String timeStamp;


    private String nonceStr;


    private String packageVal;


    private String signType;


    private String paySign;

    private String tranId;


    private String deeplink;

}
