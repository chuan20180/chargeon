package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseAgentEntity;
import com.obast.charer.enums.DiscountTypeEnum;
import com.obast.charer.enums.OrderSettleTypeEnum;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.order.OrdersSettle;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders_settle")
@ApiModel(value = "订单结算")
@AutoMapper(target = OrdersSettle.class)
public class TbOrdersSettle extends BaseAgentEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单结算类型")
    @Convert(converter = OrderSettleTypeEnum.Converter.class)
    private OrderSettleTypeEnum type;

    @ApiModelProperty(value = "原始金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "结算金额")
    private BigDecimal settledAmount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "优惠类型")
    @Convert(converter = DiscountTypeEnum.Converter.class)
    private DiscountTypeEnum discountType;

    @ApiModelProperty(value = "优惠项目关联id")
    private String discountRelateId;

    @ApiModelProperty(value = "优惠额度")
    @Type(type = "json")
    private Customer.Quota discountQuotaAmount;

    @ApiModelProperty(value = "备注")
    private String note;
}
