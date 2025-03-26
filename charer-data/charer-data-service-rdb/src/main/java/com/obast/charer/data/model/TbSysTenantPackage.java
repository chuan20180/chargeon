package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysTenantPackage;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

/**
 * 租户套餐对象 sys_tenant_package
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_tenant_package")
@AutoMapper(target = SysTenantPackage.class)
public class TbSysTenantPackage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "套餐id")
    private String id;

    @ApiModelProperty(value = "套餐名称")
    private String name;

    @ApiModelProperty(value = "关联菜单id")
    @Type(type = "json")
    private List<String> menuIds;

    @ApiModelProperty(value = "状态（1正常 0停用）")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "备注")
    private String note;
}
