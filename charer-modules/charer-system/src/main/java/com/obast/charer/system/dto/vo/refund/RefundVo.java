package com.obast.charer.system.dto.vo.refund;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.refund.Refund;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@ApiModel(value = "RefundVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Refund.class,convertGenerate = false)
public class RefundVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "流水号")
    private String tranId;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "客户用户名")
    private String userName;

    @ApiModelProperty(value = "客户余额记录id")
    private String customerBalanceId;

    @ApiModelProperty(value = "客户余额记录流水号")
    private String customerBalanceTranId;

    @ApiModelProperty(value = "支付方式识别id")
    private String paymentIdentifier;

    @ApiModelProperty(value = "支付方式名称")
    private String paymentName;

    @ApiModelProperty(value = "充值记录id")
    private String topupId;

    @ApiModelProperty(value = "充值记录流水号")
    private String topupTranId;

    @ApiModelProperty(value = "退款金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "退款时间")
    private Date refundTime;

    @ApiModelProperty(value = "退款状态")
    private RefundStateEnum state;

    @ApiModelProperty(value = "描述")
    private String note;
}
