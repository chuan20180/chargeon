package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.AppTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysApp;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 应用信息业务对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysApp.class, reverseConvertGenerate = false)
public class SysAppBo extends BaseDto {

    @NotNull(message = "id不能为空", groups = {  EditGroup.class })
    @ApiModelProperty(value = "id", required = true)
    private String id;

    @ApiModelProperty(value = "应用名称", required = true)
    private String appName;

    @ApiModelProperty(value = "应用类型", required = true)
    private AppTypeEnum appType;

    @ApiModelProperty(value = "属性")
    private String config;

    @ApiModelProperty(value = "备注", required = true)
    private String note;

    @ApiModelProperty(value = "状态", required = true)
    private EnableStatusEnum status;
}
