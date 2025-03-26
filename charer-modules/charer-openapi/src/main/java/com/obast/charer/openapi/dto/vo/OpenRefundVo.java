package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.refund.Refund;
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

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：退款记录视图
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenRefundVo")
@Data
@AutoMapper(target = Refund.class,convertGenerate = false)
public class OpenRefundVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "退款金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "支付方式id")
    private String paymentId;

    @ApiModelProperty(value = "退款时间")
    private Date refundTime;

    @ApiModelProperty(value = "退款状态")
    @Convert(converter = RefundStateEnum.Converter.class)
    private RefundStateEnum state;

    @ApiModelProperty(value = "描述")
    private String note;
}
