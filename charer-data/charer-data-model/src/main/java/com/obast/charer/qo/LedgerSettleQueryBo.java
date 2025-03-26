package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.LedgerSettleStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LedgerSettleQueryBo extends BaseDto {
    private String id;
    private String tranId;
    private String dealerName;
    private LedgerSettleStateEnum state;
}