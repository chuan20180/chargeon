package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysDealer;
import com.obast.charer.qo.SysDealerQueryBo;

import java.util.List;

public interface ISysDealerData extends ICommonData<SysDealer, String>, IJPACommonData<SysDealer, SysDealerQueryBo, String> {

    List<SysDealer> findListByAgentId(String agentId);


    SysDealer add(SysDealer sysDealer);

    SysDealer update(SysDealer sysDealer);
}
