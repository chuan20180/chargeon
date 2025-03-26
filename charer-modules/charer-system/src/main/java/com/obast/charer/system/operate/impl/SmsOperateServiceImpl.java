package com.obast.charer.system.operate.impl;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.enums.SmsTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.model.sms.SmsRecord;
import com.obast.charer.sms.core.SmsFactory;
import com.obast.charer.system.operate.ISmsOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：短信操作服务实现
 */
@Slf4j
@Service
public class SmsOperateServiceImpl implements ISmsOperateService {


    @Autowired
    private SmsFactory smsFactory;

    /**
     * 发送验证码短信
     */
    @Transactional
    @Override
    public SmsRecord sendVerifyCode(String type, String mobile) {
        SmsTypeEnum smsTypeEnum = SmsTypeEnum.valueOf(type);
        ActionResult<?> result = smsFactory.sendVerificationCode(smsTypeEnum, mobile);
        if(result.getCode() != 0) {
            throw new BizException(ErrCode.SMS_SEND_EXCEPTION, result.getReason());
        }
        return (SmsRecord) result.getData();
    }

    /**
     * 验证码验证码
     */
    @Transactional
    @Override
    public SmsRecord verifyVerificationCode(String type, String mobile, String code) {
        SmsTypeEnum smsTypeEnum = SmsTypeEnum.valueOf(type);
        ActionResult<?> result = smsFactory.verifyVerificationCode(smsTypeEnum, mobile, code);
        if(result.getCode() != 0) {
            throw new BizException(ErrCode.SMS_SEND_EXCEPTION, result.getReason());
        }
        return (SmsRecord) result.getData();
    }
}
