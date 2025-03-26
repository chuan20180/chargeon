package com.obast.charer.system.dto.vo.orders;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.converter.StringToListConverter;
import com.obast.charer.enums.DiscountTypeEnum;
import com.obast.charer.enums.OrderSettleTypeEnum;
import com.obast.charer.model.order.OrdersSettle;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OrdersSettleVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OrdersSettle.class, uses = {StringToListConverter.class}, convertGenerate = false)
public class OrdersSettleVo extends BaseDto {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "")
    private String id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单结算类型")
    private OrderSettleTypeEnum type;

    @ApiModelProperty(value = "原始金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "结算金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal settledAmount;

    @ApiModelProperty(value = "优惠金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "优惠类型")
    private DiscountTypeEnum discountType;

    @ApiModelProperty(value = "优惠相关id")
    private String discountRelateId;

    @ApiModelProperty(value = "备注")
    private String note;

}