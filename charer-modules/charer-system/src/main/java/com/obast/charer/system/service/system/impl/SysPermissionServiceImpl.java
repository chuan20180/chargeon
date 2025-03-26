package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.system.service.system.ISysMenuManageService;
import com.obast.charer.system.service.system.ISysPermissionService;
import com.obast.charer.system.service.system.ISysRoleManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    private final ISysRoleManageService sysRoleManageService;
    private final ISysMenuManageService sysMenuManageService;

    /**
     * 获取角色数据权限
     *
     * @param userId  用户id
     * @return 角色权限信息
     */
    @Override
    public Set<String> getRolePermission(String userId) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (LoginHelper.isSuperAdmin(userId)) {
            roles.add(TenantConstants.SUPER_ADMIN_ROLE_KEY);
        } else {
            roles.addAll(sysRoleManageService.selectRoleKeyByUserId(userId));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param userId  用户id
     * @return 菜单权限信息
     */
    @Override
    public Set<String> getMenuPermission(String userId) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (LoginHelper.isSuperAdmin(userId)) {
            perms.add("*:*:*");
        } else {
            perms.addAll(sysMenuManageService.selectMenuPermsByUserId(userId));
        }
        return perms;
    }
}
