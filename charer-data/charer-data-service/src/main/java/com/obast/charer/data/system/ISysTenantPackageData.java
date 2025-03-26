package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysTenantPackage;
import com.obast.charer.qo.SysTenantPackageQueryBo;

import java.util.List;

/**
 * 操作日志数据接口
 *
 * @author sjg
 */
public interface ISysTenantPackageData  extends ICommonData<SysTenantPackage, String>, IJPACommonData<SysTenantPackage, SysTenantPackageQueryBo, String> {

    List<String> findMenuListByPackageId(String packageId);

    SysTenantPackage add(SysTenantPackage sysTenantPackage);

    SysTenantPackage update(SysTenantPackage sysTenantPackage);

}
