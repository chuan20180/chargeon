package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.LedgerStateEnum;
import com.obast.charer.enums.LedgerTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LedgerQueryBo extends BaseDto {


    private String tranId;

    private LedgerTypeEnum type;

    private String name;

    private String ledgerSettleId;

    private String ledgerSettleDealerId;

    private LedgerStateEnum state;
}