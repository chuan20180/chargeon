package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.SysAgentBo;
import com.obast.charer.system.dto.vo.SysAgentVo;
import com.obast.charer.qo.SysAgentQueryBo;

import java.util.List;
/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：租户分帐服务接口
 */
public interface ISysAgentService {
    Paging<SysAgentVo> queryPageList(PageRequest<SysAgentQueryBo> pageRequest);

    List<SysAgentVo> queryList(PageRequest<SysAgentQueryBo> pageRequest);

    List<SysAgentVo> queryListByTenantId(String tenantId);

    SysAgentVo queryDetail(String stationId);

    boolean addAgent(SysAgentBo data);

    boolean updateAgent(SysAgentBo data);

    void updateStatus(SysAgentBo bo);

    void deleteAgent(String id);

    boolean batchDeleteAgent(List<String> ids);


}
