package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.LedgerSettleStateEnum;
import com.obast.charer.enums.LedgerTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LedgerSettleDealerQueryBo extends BaseDto {

    private String tranId;

    private LedgerTypeEnum type;

    private String name;

    private LedgerSettleStateEnum state;

}
