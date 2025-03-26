package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ Author：chuan
 * @ Date：2024-12-10-20:35
 * @ Version：1.0
 * @ Description：
 */
@Data
public class LedgerSettleDealerPaidBo extends BaseDto {
    private static final long serialVersionUID = -1L;

    private String id;

    @ApiModelProperty(value = "备注")
    private String note;
}
