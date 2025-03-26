package com.obast.charer.system.dto.vo.ledger;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.converter.StringToListConverter;
import com.obast.charer.enums.LedgerSettleStateEnum;
import com.obast.charer.enums.LedgerTypeEnum;
import com.obast.charer.model.DealerModel;
import com.obast.charer.model.ledger.LedgerSettleDealer;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "LedgerSettleDealerVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LedgerSettleDealer.class, uses = {StringToListConverter.class}, convertGenerate = false)
public class LedgerSettleDealerVo extends DealerModel {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "")
    private String id;

    @ApiModelProperty(value = "类型")
    private LedgerTypeEnum type;

    @ApiModelProperty(value = "结算单id")
    private String ledgerSettleId;

    @ApiModelProperty(value = "流水号")
    private String tranId;

    @ApiModelProperty(value = "分帐用户名称")
    private String tenantName;

    private String agentName;

    private String dealerName;

    @ApiModelProperty(value = "状态")
    private LedgerSettleStateEnum state;

    @ApiModelProperty(value = "结算的分成金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "结算时间")
    private Date settleTime;

    @ApiModelProperty(value = "打款时间")
    private Date paidTime;

}