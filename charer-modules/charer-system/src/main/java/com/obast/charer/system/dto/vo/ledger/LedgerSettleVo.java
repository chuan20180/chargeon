package com.obast.charer.system.dto.vo.ledger;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.LedgerSettleStateEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.ledger.LedgerSettle;
import com.obast.charer.model.ledger.LedgerSettleDealer;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Convert;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "LedgerSettleVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LedgerSettle.class,convertGenerate = false)
public class LedgerSettleVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "流水号")
    private String tranId;

    @ApiModelProperty(value = "分帐用户id")
    private String dealerId;

    @ApiModelProperty(value = "分帐用户名称")
    private String dealerName;

    @ApiModelProperty(value = "状态")
    @Convert(converter = LedgerSettleStateEnum.Converter.class)
    private LedgerSettleStateEnum state;

    @ApiModelProperty(value = "结算的分成金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "结算时间")
    private Date settleTime;

    @ApiModelProperty(value = "打款时间")
    private Date paidTime;

    List<LedgerSettleDealer> dealers;

}
