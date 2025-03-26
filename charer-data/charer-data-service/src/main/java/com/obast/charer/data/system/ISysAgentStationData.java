package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysAgent;
import com.obast.charer.model.system.SysAgentStation;
import com.obast.charer.qo.SysAgentStationQueryBo;

import java.util.List;

public interface ISysAgentStationData extends ICommonData<SysAgentStation, String>, IJPACommonData<SysAgentStation, SysAgentStationQueryBo, String> {

    SysAgent findAgentByStationId(String stationId);

    List<SysAgentStation> findByAgentId(String agentId);

    SysAgentStation add(SysAgentStation sysAgentStation);

    SysAgentStation update(SysAgentStation sysAgentStation);
}
