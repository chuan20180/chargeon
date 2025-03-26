package com.obast.charer.openapi.dto.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "MobileLoginBo")
@Data
public class MobileLoginBo implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "mobile")
    private String mobile;


    @ApiModelProperty(value = "countryId")
    private String countryId;
}
