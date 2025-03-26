package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseAgentEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysDealer;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_dealer")
@ApiModel(value = "合作商信息")
@AutoMapper(target = SysDealer.class)
public class TbSysDealer extends BaseAgentEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private String contactMobile;

    @ApiModelProperty(value = "菜单ids")
    @Type(type = "json")
    private List<String> menuIds;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "备注")
    private String note;
}