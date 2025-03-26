package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import com.obast.charer.system.dto.bo.MapConfigBo;
import com.obast.charer.system.dto.vo.map.MapConfigVo;
import com.obast.charer.qo.MapConfigQueryBo;

import java.util.List;

public interface IMapConfigManagerService {
    Paging<MapConfigVo> queryPageList(PageRequest<MapConfigQueryBo> pageRequest);

    List<MapConfigVo> queryList(PageRequest<MapConfigQueryBo> pageRequest);

    MapConfigVo queryDetail(String sysConfigId);

     MapConfigVo queryByTenantId(String tenantId);

    boolean update(MapConfigBo data);

    void updateStatus(MapConfigBo bo);

}
