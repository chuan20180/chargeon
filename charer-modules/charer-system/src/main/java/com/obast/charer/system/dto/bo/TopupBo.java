package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class TopupBo extends BaseDto {

    private String id;

    private String customerId;

    private BigDecimal arrivalAmount;

    private String note;

}
