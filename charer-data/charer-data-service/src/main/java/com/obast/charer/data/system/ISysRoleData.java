package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysRole;
import com.obast.charer.qo.SysRoleQueryBo;

import java.util.List;

/**
 * 操作日志数据接口
 *
 * @author sjg
 */
public interface ISysRoleData extends ICommonData<SysRole, String>, IJPACommonData<SysRole, SysRoleQueryBo, String> {


    SysRole add(SysRole sysRole);

    SysRole update(SysRole sysRole);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<String> selectMenuListByRoleId(String roleId, boolean menuCheckStrictly);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> findListByUserId(String userId);

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    boolean checkRoleNameUnique(SysRole role);

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    boolean checkRoleKeyUnique(SysRole role);

}
