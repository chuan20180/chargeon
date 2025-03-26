package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@ApiModel(value = "OrderSettlementBo")
@Data
@EqualsAndHashCode(callSuper = true)
public class LedgerSettleBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    private List<String> ledgerIds;


}
