package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.order.OrdersSettle;
import com.obast.charer.qo.OrdersSettleQueryBo;

import java.math.BigDecimal;
import java.util.List;


public interface IOrdersSettleData extends ICommonData<OrdersSettle, String>, IJPACommonData<OrdersSettle, OrdersSettleQueryBo, String> {

    List<OrdersSettle> findListByOrderId(String orderId);

    BigDecimal findAmountByCustomerId(String customerId);

    OrdersSettle add(OrdersSettle ordersSettle);

    OrdersSettle update(OrdersSettle ordersSettle);
}
