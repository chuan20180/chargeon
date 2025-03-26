package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.enums.ChannelIdentifierEnum;
import com.obast.charer.model.notify.ChannelConfig;
import com.obast.charer.qo.ChannelConfigQueryBo;

/**
 * author: 石恒
 * date: 2023-05-11 17:15
 * description:
 **/
public interface IChannelConfigData extends ICommonData<ChannelConfig, String>, IJPACommonData<ChannelConfig, ChannelConfigQueryBo, String> {

    ChannelConfig findByIdentifier(ChannelIdentifierEnum identifierEnum);
}
