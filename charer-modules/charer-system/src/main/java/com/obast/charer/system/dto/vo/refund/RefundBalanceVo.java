package com.obast.charer.system.dto.vo.refund;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.refund.RefundBalance;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：退款记录视图
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RefundBalanceVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = RefundBalance.class,convertGenerate = false)
public class RefundBalanceVo extends BaseDto {

    private Long id;

    private String refundId;

    private String customerBalanceId;

    private String tranId;

    private BigDecimal amount;

    private RefundStateEnum state;

    private Date successTime;

    private String userReceivedAccount;

    private String note;

    private String payId;
}
