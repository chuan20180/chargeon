package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.push.PushConfig;
import com.obast.charer.qo.PushConfigQueryBo;


public interface IPushConfigData extends ICommonData<PushConfig, String>, IJPACommonData<PushConfig, PushConfigQueryBo, String> {
    PushConfig findAvailableConfig();

    PushConfig findByIdentifier(String identifier);
}
