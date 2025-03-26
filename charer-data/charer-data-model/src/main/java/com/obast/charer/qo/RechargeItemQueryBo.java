package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Data
@EqualsAndHashCode(callSuper = true)
public class RechargeItemQueryBo extends BaseDto {

    private String id;

    private String rechargeId;

    private BigDecimal amount;

    private EnableStatusEnum status;

}
