package com.obast.charer.payment.wechat;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @ Author：chuan
 * @ Date：2024-10-30-15:24
 * @ Version：1.0
 * @ Description：WechatConfig
 */
@Slf4j
@Data
public class WechatResult {

    String code;
    String message;
    String hint;
    String seqno;
    String pay_url;
    String pay_id;
}
