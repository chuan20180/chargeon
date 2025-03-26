package com.obast.charer.sms.core;

import com.obast.charer.model.sms.SmsRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Author：chuan
 * @ Date：2024-10-05-04:40
 * @ Version：1.0
 * @ Description：SmsConfig
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsResult {

    /**
     * 状态码，0:成功，x:其它错误码
     */
    private Integer code;

    /**
     * 失败原因
     */
    private String message;

    private SmsRecord data;


    public static SmsResult success() {
        return new SmsResult(0, null, null);
    }

    public static SmsResult success(SmsRecord data) {
        return new SmsResult(0, null, data);
    }

    public static SmsResult error(Integer code, String message) {
        return new SmsResult(code, message, null);
    }

}
