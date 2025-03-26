package com.obast.charer.openapi.dto.bo;

import com.obast.charer.enums.ChargePayTypeEnum;
import com.obast.charer.enums.ChargeStartTypeEnum;
import com.obast.charer.enums.ChargeStopTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "TokenVerifyBo")
@Data
public class InstantStartChargeBo implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "ChargeStartType")
    private ChargeStartTypeEnum chargeStartType;

    @ApiModelProperty(value = "ChargeStopType")
    private ChargeStopTypeEnum chargeStopType;

    @ApiModelProperty(value = "gunId")
    private String gunId;

    @ApiModelProperty(value = "instantId")
    private String instantId;

}
