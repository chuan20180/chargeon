package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.AppTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysApp;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "sys_app")
@AutoMapper(target = SysApp.class)
@ApiModel(value = "应用信息表")
public class TbSysApp extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "appId")
    private String appId;

    @ApiModelProperty(value = "appSecret")
    private String appSecret;

    @ApiModelProperty(value = "属性")
    private String config;

    @ApiModelProperty(value = "应用类型")
    @Convert(converter = AppTypeEnum.Converter.class)
    private AppTypeEnum appType;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;


}
