package com.obast.charer.model.system;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;


/**
 * 租户套餐视图对象 sys_tenant_package
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysTenantPackage extends BaseModel implements Id<String>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 租户套餐id
     */
    private String id;

    /**
     * 套餐名称
     */
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
