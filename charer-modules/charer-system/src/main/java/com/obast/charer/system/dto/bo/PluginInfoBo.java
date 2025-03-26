package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.model.plugin.PluginInfo;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 插件业务对象
 *
 * @author sjg
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PluginInfo.class, reverseConvertGenerate = false)
public class PluginInfoBo extends BaseDto {

    /**
     * id
     */
    @NotNull(message = "插件id不能为空", groups = {EditGroup.class})
    private String id;

    /**
     * 插件名称
     */
    @NotNull(message = "插件名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String name;

    /**
     * 部署方式
     */
    @NotNull(message = "部署方式不能为空", groups = {AddGroup.class, EditGroup.class})
    private String deployType;

    @ApiModelProperty(value = "协议Key")
    @Size(max = 255, message = "协议Key长度不正确")
    private String protocolKey;

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
     * 插件配置信息
     */
    private String config;

    /**
     * 插件脚本
     */
    private String script;

}
