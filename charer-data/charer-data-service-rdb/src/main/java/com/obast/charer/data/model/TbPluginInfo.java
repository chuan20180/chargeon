package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.model.plugin.PluginInfo;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author sjg
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@ApiModel(value = "插件信息")
@Table(name = "plugin_info")
@DynamicUpdate
@AutoMapper(target = PluginInfo.class)

public class TbPluginInfo extends BaseEntity {

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "id")
    private String id;


    /**
     * 插件包id
     */
    @ApiModelProperty(value = "插件包id")
    private String pluginId;


    @ApiModelProperty(value = "协议key")
    private String protocolKey;

    /**
     * 插件名称
     */
    @ApiModelProperty(value = "插件名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 部署方式
     */
    @ApiModelProperty(value = "部署方式")
    private String deployType;

    /**
     * 插件包地址
     */
    @ApiModelProperty(value = "插件包地址")
    private String file;

    /**
     * 插件版本
     */
    @ApiModelProperty(value = "插件版本")
    private String version;

    /**
     * 插件类型
     */
    @ApiModelProperty(value = "插件类型")
    private String type;

    /**
     * 设备插件协议类型
     */
    @ApiModelProperty(value = "设备插件协议类型")
    private String protocol;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String state;

    /**
     * 插件配置项描述信息
     */
    @ApiModelProperty(value = "插件配置项描述信息")
    @Column(columnDefinition = "text")
    private String configSchema;

    /**
     * 插件配置信息
     */
    @ApiModelProperty(value = "插件配置信息")
    @Column(columnDefinition = "text")
    private String config;

    /**
     * 插件脚本
     */
    @ApiModelProperty(value = "插件脚本")
    @Column(columnDefinition = "text")
    private String script;

}
