package com.obast.charer.data.system;

import com.obast.charer.qo.SysAppQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysApp;

import java.util.List;

/**
 * 数据接口
 */
public interface ISysAppData extends ICommonData<SysApp, String>, IJPACommonData<SysApp, SysAppQueryBo, String> {

    SysApp findByAppId(String appId);

    List<SysApp> findAllByTenantId(String tenantId);

    SysApp add(SysApp sysApp);
    SysApp update(SysApp sysApp);

}
