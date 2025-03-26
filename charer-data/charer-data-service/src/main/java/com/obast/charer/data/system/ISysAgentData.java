package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysAgent;
import com.obast.charer.qo.SysAgentQueryBo;

import java.util.List;

public interface ISysAgentData extends ICommonData<SysAgent, String>, IJPACommonData<SysAgent, SysAgentQueryBo, String> {

    List<SysAgent> findListByTenantId(String tenantId);

    SysAgent add(SysAgent sysAgent);

    SysAgent update(SysAgent sysAgent);
}
