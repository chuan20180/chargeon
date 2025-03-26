package com.obast.charer.system.dto;

import lombok.Data;

/**
 * 角色和菜单关联
 *
 * @author Lion Li
 */

@Data
public class SysRoleMenu {

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;

}
