package com.obast.charer.openapi.dto.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "TokenVerifyBo")
@Data
public class TokenVerifyBo implements Serializable {
    private static final long serialVersionUID = -1L;

    @NotBlank(message = "appid不能为空")
    @ApiModelProperty(value = "appid")
    private String appId;

    @NotBlank(message = "appid不能为空")
    @ApiModelProperty(value = "appid")
    private String appSecret;

    @NotBlank(message = "timestamp不能为空")
    @ApiModelProperty(value = "时间戳")
    private String timestamp;

    @NotNull(message = "{tenant.number.not.blank}")
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

}
