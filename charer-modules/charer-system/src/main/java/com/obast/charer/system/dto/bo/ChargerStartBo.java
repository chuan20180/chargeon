package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "ChargerStartBo")
@Data
@EqualsAndHashCode(callSuper = true)
public class ChargerStartBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @NotBlank(message = "充电桩不能为空")
    private String id;

    @NotBlank(message = "枪号不能为空")
    private String gunId;

}
