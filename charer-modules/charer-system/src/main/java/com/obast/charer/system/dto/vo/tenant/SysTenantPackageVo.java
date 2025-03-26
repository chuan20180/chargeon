package com.obast.charer.system.dto.vo.tenant;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysTenantPackage;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 租户套餐视图对象 sys_tenant_package
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysTenantPackage.class, convertGenerate = false)
public class SysTenantPackageVo extends BaseDto {
    private static final long serialVersionUID = 1L;

    /**
     * 租户套餐id
     */
    @ExcelProperty(value = "租户套餐id")
    private String id;

    /**
     * 套餐名称
     */
    @ExcelProperty(value = "套餐名称")
    private String name;

    /**
     * 关联菜单id
     */
    @ExcelProperty(value = "关联菜单id")
    private List<String> menuIds;

    /**
     * 关联菜单id
     */
    @ExcelProperty(value = "选中的菜单id")
    private List<String> checkedMenuIds;

    /**
     * 状态（1正常 0停用）
     */
    @ExcelProperty(value = "状态")
    private EnableStatusEnum status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String note;

}
