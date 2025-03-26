package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysAgentStationDealer;
import com.obast.charer.qo.SysAgentStationDealerQueryBo;

import java.util.List;

public interface ISysAgentStationDealerData extends ICommonData<SysAgentStationDealer, String>, IJPACommonData<SysAgentStationDealer, SysAgentStationDealerQueryBo, String> {


    List<SysAgentStationDealer> findByAgentId(String agentId);

    List<SysAgentStationDealer> findByDealerId(String dealerId);


    List<SysAgentStationDealer> findByAgentStationId(String agentStationId);

    SysAgentStationDealer add(SysAgentStationDealer sysAgentStationDealer);

    SysAgentStationDealer update(SysAgentStationDealer sysAgentStationDealer);
}
