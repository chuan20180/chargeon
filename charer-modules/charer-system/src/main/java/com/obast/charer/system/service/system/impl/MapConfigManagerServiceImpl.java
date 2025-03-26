package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IMapConfigData;
import com.obast.charer.system.dto.bo.MapConfigBo;
import com.obast.charer.system.dto.vo.map.MapConfigVo;
import com.obast.charer.system.service.system.IMapConfigManagerService;
import com.obast.charer.model.map.MapConfig;
import com.obast.charer.qo.MapConfigQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：地图配置管理服务实现
 */
@Service
public class MapConfigManagerServiceImpl implements IMapConfigManagerService {

    @Autowired
    private IMapConfigData mapConfigData;

    @Override
    public Paging<MapConfigVo> queryPageList(PageRequest<MapConfigQueryBo> pageRequest) {
        return MapstructUtils.convert(mapConfigData.findPage(pageRequest), MapConfigVo.class);
    }

    @Override
    public List<MapConfigVo> queryList(PageRequest<MapConfigQueryBo> pageRequest) {
        return MapstructUtils.convert(mapConfigData.findList(pageRequest.getData()), MapConfigVo.class);
    }

    @Override
    public MapConfigVo queryDetail(String sysConfigId) {
        return MapstructUtils.convert(mapConfigData.findById(sysConfigId), MapConfigVo.class);
    }

    @Override
    public MapConfigVo queryByTenantId(String tenantId) {
        return MapstructUtils.convert(mapConfigData.findMapConfigByTenantId(tenantId), MapConfigVo.class);
    }



    @Override
    public boolean update(MapConfigBo bo) {
        MapConfig di = bo.to(MapConfig.class);
        return mapConfigData.save(di) != null;
    }

    @Override
    public void updateStatus(MapConfigBo bo) {
        MapConfig mapConfig = mapConfigData.findById(bo.getId());
        mapConfig.setStatus(bo.getStatus());
        mapConfigData.save(mapConfig);
    }
}