package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.SysAgentStationDealerBo;
import com.obast.charer.system.dto.bo.SysAgentStationDealerSaveBo;
import com.obast.charer.system.dto.vo.SysAgentStationDealerVo;
import com.obast.charer.qo.SysAgentStationDealerQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：代理商和合作商绑定管理服务接口
 */
public interface ISysAgentStationDealerService {
    Paging<SysAgentStationDealerVo> queryPageList(PageRequest<SysAgentStationDealerQueryBo> pageRequest);

    List<SysAgentStationDealerVo> queryList(PageRequest<SysAgentStationDealerQueryBo> pageRequest);

    SysAgentStationDealerVo queryDetail(String id);

    boolean save(SysAgentStationDealerSaveBo data);

    void updateStatus(SysAgentStationDealerBo bo);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);


}
