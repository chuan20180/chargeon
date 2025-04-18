package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.SysRoleBo;
import com.obast.charer.system.dto.vo.system.SysRoleVo;

/**
 * 角色业务层
 *
 * @author Lion Li
 */
public interface ISysRoleService {


    Paging<SysRoleVo> selectPageRoleList(PageRequest<SysRoleBo> query);

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
//     * @return 角色数据集合信息
//     */
//    List<SysRoleVo> selectRoleList(SysRoleBo role);
//
//    /**
//     * 根据用户ID查询角色列表
//     *
//     * @param userId 用户ID
//     * @return 角色列表
//     */
//    List<SysRoleVo> selectRolesByUserId(String userId);
//
//    /**
//     * 根据用户ID查询角色权限
//     *
//     * @param userId 用户ID
//     * @return 权限列表
//     */
//    Set<String> selectRolePermissionByUserId(String userId);
//
//    /**
//     * 查询所有角色
//     *
//     * @return 角色列表
//     */
//    List<SysRoleVo> selectRoleAll();
//
//    /**
//     * 根据用户ID获取角色选择框列表
//     *
//     * @param userId 用户ID
//     * @return 选中角色ID列表
//     */
//    List<String> selectRoleListByUserId(String userId);
//
//    /**
//     * 通过角色ID查询角色
//     *
//     * @param roleId 角色ID
//     * @return 角色对象信息
//     */
//    SysRoleVo selectRoleById(String roleId);
//
//    /**
//     * 校验角色名称是否唯一
//     *
//     * @param role 角色信息
//     * @return 结果
//     */
//    boolean checkRoleNameUnique(SysRoleBo role);
//
//    /**
//     * 校验角色权限是否唯一
//     *
//     * @param role 角色信息
//     * @return 结果
//     */
//    boolean checkRoleKeyUnique(SysRoleBo role);
//
//    /**
//     * 校验角色是否允许操作
//     *
//     * @param roleId 角色ID
//     */
//    void checkRoleAllowed(String roleId);
//
//    /**
//     * 校验角色是否有数据权限
//     *
//     * @param roleId 角色id
//     */
//    void checkRoleDataScope(String roleId);
//
//    /**
//     * 通过角色ID查询角色使用数量
//     *
//     * @param roleId 角色ID
//     * @return 结果
//     */
//    long countUserRoleByRoleId(String roleId);
//
//    /**
//     * 新增保存角色信息
//     *
//     * @param bo 角色信息
//     * @return 结果
//     */
//    int insertRole(SysRoleBo bo);
//
//    /**
//     * 修改保存角色信息
//     *
//     * @param bo 角色信息
//     * @return 结果
//     */
//    int updateRole(SysRoleBo bo);
//
//    /**
//     * 修改角色状态
//     *
//     * @param roleId 角色ID
//     * @param status 角色状态
//     * @return 结果
//     */
//    void updateRoleStatus(String roleId, String status);
//
//    /**
//     * 修改数据权限信息
//     *
//     * @param bo 角色信息
//     * @return 结果
//     */
//    int authDataScope(SysRoleBo bo);
//
//    /**
//     * 通过角色ID删除角色
//     *
//     * @param roleId 角色ID
//     * @return 结果
//     */
//    void deleteRoleById(String roleId);
//
//    /**
//     * 批量删除角色信息
//     *
//     * @param roleIds 需要删除的角色ID
//     * @return 结果
//     */
//    void deleteRoleByIds(Collection<String> roleIds);
//
//    /**
//     * 取消授权用户角色
//     *
//     * @param userRole 用户和角色关联信息
//     * @return 结果
//     */
//    void deleteAuthUser(SysUserRole userRole);
//
//    /**
//     * 批量取消授权用户角色
//     *
//     * @param roleId  角色ID
//     * @param userIds 需要取消授权的用户数据ID
//     * @return 结果
//     */
//    void deleteAuthUsers(String roleId, String[] userIds);
//
//    /**
//     * 批量选择授权用户角色
//     *
//     * @param roleId  角色ID
//     * @param userIds 需要删除的用户数据ID
//     * @return 结果
//     */
//    void insertAuthUsers(String roleId, String[] userIds);
//
//    void cleanOnlineUserByRole(String roleId);
}
