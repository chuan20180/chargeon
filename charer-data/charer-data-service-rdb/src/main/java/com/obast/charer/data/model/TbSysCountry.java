package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.system.SysCountry;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_country")
@ApiModel(value = "国家代码")
@AutoMapper(target = SysCountry.class)
public class TbSysCountry extends BaseTenantEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    @ApiModelProperty(value = "名称")
    @Type(type = "json")
    private I18nField name;

    @ApiModelProperty(value = "Iso2")
    private String iso2;

    @ApiModelProperty(value = "ISO3")
    private String iso3;

    @ApiModelProperty(value = "国际区号")
    private String tel;

    @ApiModelProperty(value = "国际区号正则")
    private String telRule;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;

}