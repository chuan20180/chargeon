package com.obast.charer.qo;

import lombok.Data;


@Data
public class PluginInfoQueryBo {

    /**
     * id
     */
    private String id;

    /**
     * 插件包id
     */
    private String pluginId;

    /**
     * 插件名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 部署方式
     */
    private String deployType;

    /**
     * 插件包文件名
     */
    private String file;

    /**
     * 插件版本
     */
    private String version;

    /**
     * 插件类型
     */
    private String type;

    /**
     * 设备插件协议类型
     */
    private String protocol;

    /**
     * 状态
     */
    private String state;

    /**
     * 插件配置项描述信息
     */
    private String configSchema;

    /**
     * 插件配置信息
     */
    private String config;

    /**
     * 插件脚本
     */
    private String script;


}
