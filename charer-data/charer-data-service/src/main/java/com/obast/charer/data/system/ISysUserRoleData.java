package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.model.system.SysUserRole;

import java.util.List;

/**
 * 用户角色数据接口
 *
 * @author sjg
 */
public interface ISysUserRoleData extends ICommonData<SysUserRole, String> {
    /**
     * 按用户id删除数据
     *
     * @param userId 用户id
     * @return 数量
     */
    int deleteByUserId(String userId);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    long countUserRoleByRoleId(String roleId);

    void delete(String roleId, List<String> userIds);

    long insertBatch(List<SysUserRole> list);
}
