package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.SysCountryBo;
import com.obast.charer.system.dto.vo.SysCountryVo;
import com.obast.charer.qo.SysCountryQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：场站管理服务接口
 */
public interface ISysCountryManagerService {
    Paging<SysCountryVo> queryPageList(PageRequest<SysCountryQueryBo> pageRequest);

    List<SysCountryVo> queryList(PageRequest<SysCountryQueryBo> pageRequest);

    SysCountryVo queryDetail(String stationId);

    boolean add(SysCountryBo data);

    boolean update(SysCountryBo data);

    void updateStatus(SysCountryBo bo);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);


}
