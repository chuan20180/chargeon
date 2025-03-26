package com.obast.charer.system.dto.vo;

import com.obast.charer.system.dto.vo.tenant.SysTenantVo;
import lombok.Data;

import java.util.Set;

/**
 * 登录用户信息
 *
 * @author Michelle.Chung
 */
@Data
public class UserInfoVo {

    /**
     * 用户基本信息
     */
    private SysUserVo user;

    /**
     * 租户
     */
    private SysTenantVo tenant;

    /**
     * 菜单权限
     */
    private Set<String> permissions;

    /**
     * 角色权限
     */
    private Set<String> roles;


}
