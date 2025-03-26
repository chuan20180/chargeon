package com.obast.charer.openapi.dto.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "ChargeScanBo")
@Data
public class ChargeScanBo implements Serializable {
    private static final long serialVersionUID = -1L;

    private String domain;

    private String product;

    private String productKey;

    private String action;

    private String no;

}
