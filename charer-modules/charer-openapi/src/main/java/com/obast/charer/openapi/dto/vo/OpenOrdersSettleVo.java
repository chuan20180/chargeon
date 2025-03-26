package com.obast.charer.openapi.dto.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.DiscountTypeEnum;
import com.obast.charer.enums.OrderSettleTypeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.order.OrdersSettle;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import java.io.Serializable;
import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenOrdersSettleVo")
@Data
@AutoMapper(target = OrdersSettle.class)
public class OpenOrdersSettleVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单结算类型")
    @Convert(converter = OrderSettleTypeEnum.Converter.class)
    private OrderSettleTypeEnum type;

    @ApiModelProperty(value = "订单金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "扣款金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal settledAmount;

    @ApiModelProperty(value = "优惠金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal discountAmount;

    private DiscountTypeEnum discountType;

    private String discountRelateId;
    
    private String note;
}
