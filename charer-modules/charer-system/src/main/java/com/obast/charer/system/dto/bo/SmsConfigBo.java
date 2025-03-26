package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.TplTypeEnum;
import com.obast.charer.model.sms.SmsConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@ApiModel(value = "SmsConfigBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SmsConfig.class)
public class SmsConfigBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private EnableStatusEnum status;

    private TplTypeEnum tplType;

    @ApiModelProperty(value = "属性")
    private String properties;
}