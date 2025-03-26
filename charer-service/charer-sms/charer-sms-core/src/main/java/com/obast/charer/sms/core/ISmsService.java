package com.obast.charer.sms.core;

public interface ISmsService {

    String getIdentifier();

    void setProperties(String smsProperties);

    ISmsProperties getProperties();

    SmsResult sendVerificationCode(String id, String message, String phoneNumber);

    SmsResult sendNotify(String id, String message, String phoneNumber);



}
