package com.obast.charer.sms.core;

import lombok.Data;

import java.io.Serializable;

/**
 * author: 石恒
 * date: 2023-05-08 15:15
 * description:
 **/

@Data
public class SmsMessage implements Serializable {
    String code;
}
