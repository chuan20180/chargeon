package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.SysAgentStationBo;
import com.obast.charer.system.dto.vo.SysAgentStationVo;
import com.obast.charer.qo.SysAgentStationQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：租户分帐服务接口
 */
public interface ISysAgentStationService {
    Paging<SysAgentStationVo> queryPageList(PageRequest<SysAgentStationQueryBo> pageRequest);

    List<SysAgentStationVo> queryList(PageRequest<SysAgentStationQueryBo> pageRequest);

    SysAgentStationVo queryDetail(String id);

    boolean add(SysAgentStationBo data);

    void updateStatus(SysAgentStationBo bo);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);


}
