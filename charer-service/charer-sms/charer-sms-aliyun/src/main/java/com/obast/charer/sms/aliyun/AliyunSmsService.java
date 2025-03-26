package com.obast.charer.sms.aliyun;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.data.business.ISmsRecordData;
import com.obast.charer.sms.core.ISmsService;
import com.obast.charer.sms.core.SmsResult;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class AliyunSmsService implements ISmsService {

    String identifier = "Aliyun";


    AliyunSmsProperties properties;

    @Autowired
    private ISmsRecordData smsRecordData;

    @Override
    public void setProperties(String smsProperties) {
        try {
            properties = JSONObject.parseObject(smsProperties, AliyunSmsProperties.class);
        } catch (Exception e) {
            throw new BizException(ErrCode.PARSE_JSON_EXCEPTION);
        }
    }

    @Override
    public AliyunSmsProperties getProperties() {
        return properties;
    }

    @Override
    public SmsResult sendNotify(String id, String message, String phoneNumber) {
       return new SmsResult(0, null, null);
    }

    @Override
    public SmsResult sendVerificationCode(String id, String message, String phoneNumber) {

        AliyunSmsProperties aliyunSmsProperties = getProperties();


        return  SmsResult.success();

    }
}