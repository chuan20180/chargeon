package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;

import com.obast.charer.enums.ChargePayTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrePayQueryBo extends BaseDto {

    private String payment;

    private String rechargeItemId;

    private String openId;

    private BigDecimal amount;

    ChargePayTypeEnum payType;
}