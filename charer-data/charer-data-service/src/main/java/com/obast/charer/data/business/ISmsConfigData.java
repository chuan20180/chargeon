package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.sms.SmsConfig;
import com.obast.charer.qo.SmsConfigQueryBo;

public interface ISmsConfigData extends ICommonData<SmsConfig, String>, IJPACommonData<SmsConfig, SmsConfigQueryBo, String> {
    SmsConfig findAvaiableSmsConfig();
}
