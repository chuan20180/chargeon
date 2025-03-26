package com.obast.charer.data.platform;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.protocol.Protocol;
import com.obast.charer.qo.ProtocolQueryBo;

/**
 * 产品接口
 */
public interface IProtocolData extends ICommonData<Protocol, String>, IJPACommonData<Protocol, ProtocolQueryBo, String> {

    Protocol findByProtocolKey(String productKey);

}
