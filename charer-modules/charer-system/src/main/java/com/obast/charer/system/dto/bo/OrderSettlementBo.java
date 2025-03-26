package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@ApiModel(value = "OrderSettlementBo")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderSettlementBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    private String id;

    @ApiModelProperty(value = "备注")
    private String note;


}
