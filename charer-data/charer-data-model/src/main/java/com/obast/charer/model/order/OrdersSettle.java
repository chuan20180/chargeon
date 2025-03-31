package com.obast.charer.model.order;

import com.obast.charer.enums.DiscountTypeEnum;
import com.obast.charer.enums.OrderSettleTypeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import com.obast.charer.model.customer.Customer;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersSettle extends BaseModel implements Id<String>, Serializable {

    private String id;
    private String customerId;
    private String orderId;
    private OrderSettleTypeEnum type;
    private BigDecimal amount;
    private BigDecimal settledAmount;
    private BigDecimal discountAmount;
    private DiscountTypeEnum discountType;
    private String discountRelateId;
    private Customer.Quota discountQuotaAmount;
    private String note;
}