package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.alert.AlertConfig;
import com.obast.charer.qo.AlertConfigQueryBo;


public interface IAlertConfigData extends ICommonData<AlertConfig, String>, IJPACommonData<AlertConfig, AlertConfigQueryBo, String> {

}
