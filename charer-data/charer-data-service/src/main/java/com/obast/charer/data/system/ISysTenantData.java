package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysTenant;
import com.obast.charer.qo.SysTenantQueryBo;

import java.util.List;

/**
 * 租户数据接口
 *
 * @author tfd
 */
public interface ISysTenantData  extends ICommonData<SysTenant, String>, IJPACommonData<SysTenant, SysTenantQueryBo, String> {

    boolean checkCompanyNameUnique(SysTenant to);

    List<SysTenant> findListByPackageId(String packageId);

    SysTenant add(SysTenant sysTenant);

    SysTenant update(SysTenant sysTenant);

}
