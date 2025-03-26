package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.push.PushConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;

@ApiModel(value = "PushConfigBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PushConfig.class)
public class PushConfigBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    private String name;

    public String identifier;

    @ApiModelProperty(value = "属性")
    private String properties;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;
}