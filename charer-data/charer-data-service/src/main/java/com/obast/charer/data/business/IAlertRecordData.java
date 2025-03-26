
package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.alert.AlertRecord;
import com.obast.charer.qo.AlertRecordQueryBo;


public interface IAlertRecordData extends ICommonData<AlertRecord, String>, IJPACommonData<AlertRecord, AlertRecordQueryBo, String> {


}
