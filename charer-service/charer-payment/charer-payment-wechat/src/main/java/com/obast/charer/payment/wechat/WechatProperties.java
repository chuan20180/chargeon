package com.obast.charer.payment.wechat;

import lombok.Data;

/**
 * @ Author：chuan
 * @ Date：2024-10-30-15:24
 * @ Version：1.0
 * @ Description：PyunConfig
 */

@Data
public class WechatProperties {

    private String appId;
    private String merchantId;

    private String privateKey;

    private String merchantSerialNumber;

    private String apiV3Key;
}