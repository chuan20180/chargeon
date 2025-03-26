package com.obast.charer.openapi.dto.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "TokenVerifyBo")
@Data
public class MobileCodeBo implements Serializable {

    @NotBlank(message = "手机号码不能为空")
    @ApiModelProperty(value = "mobile")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "code")
    private String code;
}
