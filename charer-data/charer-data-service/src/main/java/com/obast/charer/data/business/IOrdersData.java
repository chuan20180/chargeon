package com.obast.charer.data.business;

import com.obast.charer.enums.OrderStateEnum;
import com.obast.charer.qo.OrdersQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.order.Orders;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IOrdersData extends ICommonData<Orders, String>, IJPACommonData<Orders, OrdersQueryBo, String> {


    Map<String, Object> findSummary();

    Map<String, Object> findSummaryByCreateTime(String startTime, String endTime);

    Orders findById(String id);

    Orders findByTranId(String id);

    Orders findByParkId(String id);

    Orders findByPark(String chargerId, String gunNo, Date inTime, Date outTime);

    List<Orders> findByCustomerIdAndState(String customerId, OrderStateEnum state);

    List<Orders> findWaitStartOrders();

    List<Orders> findWaitStopOrders();

    List<Orders> findByUserName(String userName);

    Orders add(Orders orders);

    Orders update(Orders orders);

}
