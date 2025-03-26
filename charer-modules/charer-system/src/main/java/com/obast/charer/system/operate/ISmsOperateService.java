package com.obast.charer.system.operate;

import com.obast.charer.model.sms.SmsRecord;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：短信操作服务接口
 */
public interface ISmsOperateService {

    SmsRecord sendVerifyCode(String type, String mobile);


    SmsRecord verifyVerificationCode(String type, String mobile, String code);

}
