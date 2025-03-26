
package com.obast.charer.openapi.dto.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "TokenVerifyBo")
@Data
public class AppSyncUserBo implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "cid")
    private String cid;


}
