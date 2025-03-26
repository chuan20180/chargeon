package com.obast.charer.sms.core;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.enums.SmsTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.data.business.ISmsConfigData;
import com.obast.charer.data.business.ISmsRecordData;
import com.obast.charer.model.sms.SmsConfig;
import com.obast.charer.model.sms.SmsRecord;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @ Author：chuan
 * @ Date：2024-10-30-11:23
 * @ Version：1.0
 * @ Description：PaymentProvider
 */
@Slf4j
@Component
public class SmsFactory {

    @Autowired
    ISmsConfigData smsConfigData;

    @Autowired
    private ISmsRecordData smsRecordData;

    private ISmsService getSmsService() {

        SmsConfig smsConfig = smsConfigData.findAvaiableSmsConfig();
        if(smsConfig == null) {
            throw new BizException(ErrCode.SMS_NOT_FOUND_AVAIABLE_PROVIDER);
        }

        ISmsService smsService = SmsProvider.getSmsService(smsConfig.getIdentifier());

        if(smsService == null) {
            throw new BizException(ErrCode.SMS_NOT_FOUND_AVAIABLE_SERVICE);
        }

        smsService.setProperties(smsConfig.getProperties());

        return smsService;
    }

    public SmsResult sendNotify(String message, String phoneNumber) {

        log.debug("[通知调试]短信发送开始, message={}, phoneNumber={}", message, phoneNumber);

        ISmsService smsService = getSmsService();

        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setMobile(phoneNumber);
        smsRecord.setType(SmsTypeEnum.Notify.name());

        smsRecord.setIdentifier(smsService.getIdentifier());
        smsRecord.setResult(0);
        SmsRecord savedSmsRecord = smsRecordData.save(smsRecord);

        String id = savedSmsRecord.getId().substring(0,12);

        SmsResult result = smsService.sendNotify(id, message, phoneNumber);

        log.debug("[通知调试]短信发送结果, message={}, phoneNumber={}, result={}", message, phoneNumber,result);


        if(result == null) {
            throw new BizException(ErrCode.SMS_SEND_FAIL, String.format("%s(%s)", ErrCode.SMS_SEND_FAIL.getValue(), "result is null"));
        }

        if(!result.getCode().equals(0)) {
            throw new BizException(ErrCode.SMS_SEND_FAIL, String.format("%s(%s)", ErrCode.SMS_SEND_FAIL.getValue(), result.getMessage()));
        }

        savedSmsRecord.setResult(1);
        smsRecordData.save(savedSmsRecord);
        return SmsResult.success();
    }

    public ActionResult<?> sendVerificationCode(SmsTypeEnum type, String phoneNumber) {

        log.debug("开始请求: {} {}", type,phoneNumber);

        ISmsService smsService = getSmsService();

        Long retryCount = smsRecordData.findCountByMobileInMinute(phoneNumber, 5);

        if(retryCount > 3) {
            throw new BizException(ErrCode.SMS_RETRY_COUNT_EXCEEDS_LIMIT);
        }

        SecureRandom random = new SecureRandom();
        String code = String.valueOf(random.nextInt(9000) + 1000);

        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setCode(code);

        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setMobile(phoneNumber);
        smsRecord.setType(type.name());
        smsRecord.setMessage(JSONObject.toJSONString(smsMessage));
        smsRecord.setIdentifier(smsService.getIdentifier());
        smsRecord.setResult(0);
        SmsRecord savedSmsRecord = smsRecordData.save(smsRecord);

        String message = String.format("message code: %s", code);

        String id = savedSmsRecord.getId().substring(0,12);

        SmsResult result = smsService.sendVerificationCode(id, message, phoneNumber);

        if(result == null) {
            throw new BizException(ErrCode.SMS_SEND_FAIL, String.format("%s(%s)", ErrCode.SMS_SEND_FAIL.getValue(), "result is null"));
        }

        if(!result.getCode().equals(0)) {
            throw new BizException(ErrCode.SMS_SEND_FAIL, String.format("%s(%s)", ErrCode.SMS_SEND_FAIL.getValue(), result.getMessage()));
        }

        savedSmsRecord.setResult(1);
        savedSmsRecord = smsRecordData.save(savedSmsRecord);
        return ActionResult.success(savedSmsRecord);
    }


    public ActionResult<?> verifyVerificationCode(SmsTypeEnum type, String phoneNumber, String code) {

        log.debug("开始请求: {} {}", type,phoneNumber);

        ISmsService smsService = getSmsService();

        SmsRecord existSmsRecord = smsRecordData.findLastByMobile(phoneNumber);

        if(existSmsRecord == null) {
            throw new BizException(ErrCode.APP_SMS_RECORD_NOT_FOUND);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -5);
        Date fiveMinutesAgo = calendar.getTime();

        if(existSmsRecord.getCreateTime().compareTo(fiveMinutesAgo) < 0) {
            throw new BizException(ErrCode.APP_SMS_VERIFY_CODE_EXPIRED);
        }

        SmsMessage smsMessage;
        try {
            smsMessage = JsonUtils.parse(existSmsRecord.getMessage(), SmsMessage.class);
        } catch (Exception e) {
            throw new BizException(ErrCode.SMS_SEND_EXCEPTION);
        }

        if(smsMessage == null || smsMessage.getCode() == null) {
            throw new BizException(ErrCode.SMS_SEND_EXCEPTION);
        }

        if(!smsMessage.getCode().equals(code)) {
            throw new BizException(ErrCode.APP_SMS_VERIFY_CODE_ERROR);
        }
        return ActionResult.success();
    }
}