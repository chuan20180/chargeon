package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.sms.SmsRecord;
import com.obast.charer.qo.SmsRecordQueryBo;

public interface ISmsRecordData extends ICommonData<SmsRecord, String>, IJPACommonData<SmsRecord, SmsRecordQueryBo, String> {

    Long findCountByMobileInMinute(String mobile, Integer minute);

    SmsRecord findLastByMobile(String mobile);

    SmsRecord save(SmsRecord smsRecord);
}
