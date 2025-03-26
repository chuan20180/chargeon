package com.obast.charer.model.system;

import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * author: 石恒
 * date: 2023-05-30 10:57
 * description:
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleMenu extends BaseModel implements Id<String>, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;
}
