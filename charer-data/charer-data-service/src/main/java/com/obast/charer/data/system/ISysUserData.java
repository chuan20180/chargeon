package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysUser;
import com.obast.charer.qo.SysUserQueryBo;

/**
 * 用户数据接口
 *
 * @author sjg
 */
public interface ISysUserData extends ICommonData<SysUser, String>, IJPACommonData<SysUser, SysUserQueryBo, String> {

    SysUser add(SysUser sysUser);

    SysUser update(SysUser sysUser);

    long countByDeptId(String deptId);

    boolean checkUserNameUnique(SysUser sysUser);

    SysUser findByPhone(String phone);

    SysUser findByUserName(String userName);

}
