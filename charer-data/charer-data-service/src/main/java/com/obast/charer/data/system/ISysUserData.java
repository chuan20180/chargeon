package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysUser;
import com.obast.charer.qo.SysUserQueryBo;

import java.util.List;

/**
 * 用户数据接口
 *
 * @author sjg
 */
public interface ISysUserData extends ICommonData<SysUser, String>, IJPACommonData<SysUser, SysUserQueryBo, String> {

    SysUser add(SysUser sysUser);

    SysUser update(SysUser sysUser);

    /**
     * 按部门统计数量
     *
     * @param deptId 部门id
     * @return 数量
     */
    long countByDeptId(String deptId);

    boolean checkUserNameUnique(SysUser sysUser);

    SysUser findByPhone(String phone);

    SysUser findByUserName(String userName);

}
