package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.constant.UserConstants;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysRole;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 角色信息业务对象 sys_role
 *
 * @author Michelle.Chung
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysRole.class, reverseConvertGenerate = false)
public class SysRoleBo extends BaseDto {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 30, message = "角色名称长度不能超过{max}个字符")
    private String roleName;


    /**
     * 角色权限字符串
     */
    @NotBlank(message = "角色Key不能为空", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 30, message = "角色Key长度不能超过{max}个字符")
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private String dataScope;

    /**
     * 菜单树选择项是否关联显示
     */
    private Boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示
     */
    private Boolean deptCheckStrictly;

    /**
     * 角色状态（0正常 1停用）
     */
    private EnableStatusEnum status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单组
     */
    private String[] menuIds;

    /**
     * 部门组（数据权限）
     */
    private String[] deptIds;

    public SysRoleBo(String roleId) {
        this.id = roleId;
    }

    public boolean isSuperAdmin() {
        return UserConstants.SUPER_ADMIN_ID.equals(this.id);
    }

}
