package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysTenantPackage;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 租户套餐业务对象 sys_tenant_package
 *
 * @author Michelle.Chung
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysTenantPackage.class, reverseConvertGenerate = false)
public class SysTenantPackageBo extends BaseDto {

    /**
     * 套餐id
     */
    @NotNull(message = "套餐id不能为空", groups = {EditGroup.class})
    private String id;

    /**
     * 套餐名称
     */
    @NotBlank(message = "套餐名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String name;

    /**
     * 关联菜单id
     */
    private List<String> menuIds;

    /**
     * 状态（1正常 0停用）
     */
    private EnableStatusEnum status;

    /**
     * 备注
     */
    private String note;

}
