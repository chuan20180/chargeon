package com.obast.charer.openapi.dto.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.RechargeTypeEnum;
import com.obast.charer.enums.TopupStateEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Instant;
import com.obast.charer.model.topup.Topup;
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
 * @ Description：充值记录视图
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenTopupVo")
@Data
@AutoMapper(target = Instant.class,convertGenerate = false)
public class OpenInstantVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "客户用户名")
    private String userName;

    @ApiModelProperty(value = "流水号")
    private String tranId;

    @ApiModelProperty(value = "充值类型")
    private RechargeTypeEnum type;

    @ApiModelProperty(value = "充值金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal topupAmount;

    @ApiModelProperty(value = "到账金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal arrivalAmount;

    @ApiModelProperty(value = "支付金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal paidAmount;

    @ApiModelProperty(value = "已用金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal usedAmount;

    @ApiModelProperty(value = "支付方式id")
    private String paymentIdentifier;

    @ApiModelProperty(value = "支付方式名称")
    private String paymentName;

    @ApiModelProperty(value = "充值时间")
    private Date topupTime;

    @ApiModelProperty(value = "付款时间")
    private Date payTime;

    @ApiModelProperty(value = "充值状态")
    private TopupStateEnum state;

    @ApiModelProperty(value = "描述")
    private String note;
}
