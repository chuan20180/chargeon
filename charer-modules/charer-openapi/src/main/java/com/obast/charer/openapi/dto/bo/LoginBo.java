package com.obast.charer.openapi.dto.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "TokenVerifyBo")
@Data
public class LoginBo implements Serializable {
    private static final long serialVersionUID = -1L;

    @NotBlank(message = "手机号码不能为空")
    @ApiModelProperty(value = "mobile")
    private String mobile;

    @NotBlank(message = "手机号码不能为空")
    @ApiModelProperty(value = "code")
    private Integer code;

    @NotBlank(message = "messageId不能为空")
    @ApiModelProperty(value = "MessageId")
    private String messageId;

}
