package com.obast.charer.openapi.dto.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "ChargeScanBo")
@Data
public class BalancePendBo implements Serializable {
    private static final long serialVersionUID = -1L;

    private String dn;

    private String gunNo;

}
