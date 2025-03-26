package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;

import com.obast.charer.model.map.MapConfig;
import com.obast.charer.qo.MapConfigQueryBo;

public interface IMapConfigData extends ICommonData<MapConfig, String>, IJPACommonData<MapConfig, MapConfigQueryBo, String> {
    MapConfig findMapConfigByTenantId(String tenantId);
}
