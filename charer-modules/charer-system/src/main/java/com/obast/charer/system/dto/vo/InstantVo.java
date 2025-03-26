package com.obast.charer.system.dto.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.*;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.topup.Topup;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值记录视图
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TopupVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Topup.class,convertGenerate = false)
public class InstantVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "ID", order = 1)
    @ColumnWidth(30)
    private String id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "客户用户名")
    @ExcelProperty(value = "客户用户名", order = 4)
    @ColumnWidth(20)
    private String userName;

    @ApiModelProperty(value = "流水号")
    @ExcelProperty(value = "流水号", order = 6)
    @ColumnWidth(26)
    private String tranId;

    @ApiModelProperty(value = "充值类型")
    private RechargeTypeEnum rechargeType;

    @ApiModelProperty(value = "充值金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private String topupAmount;

    @ApiModelProperty(value = "到账金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal arrivalAmount;

    @ApiModelProperty(value = "支付金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal paidAmount;

    @ApiModelProperty(value = "已退款金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal refundedAmount;

    @ApiModelProperty(value = "退款锁定")
    @Convert(converter = LockEnum.Converter.class)
    private LockEnum refundLocked;

    @ApiModelProperty(value = "支付方式id")
    private String paymentIdentifier;

    @ApiModelProperty(value = "支付方式名称")
    private String paymentName;

    @ApiModelProperty(value = "充值时间")
    private Date topupTime;

    @ApiModelProperty(value = "付款时间")
    private Date payTime;

    @ApiModelProperty(value = "付款id")
    private String payId;

    private String bankType;

    private String tradeStateDesc;

    private String tradeType;

    @ApiModelProperty(value = "充值状态")
    private InstantStateEnum state;

    @ApiModelProperty(value = "描述")
    private String note;
}
