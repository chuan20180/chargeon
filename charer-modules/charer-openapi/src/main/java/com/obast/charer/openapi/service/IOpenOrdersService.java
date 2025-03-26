package com.obast.charer.openapi.service;

import com.obast.charer.enums.OrderStateEnum;
import com.obast.charer.openapi.dto.vo.OpenOrdersVo;
import com.obast.charer.qo.OrdersQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩订单服务接口
 */
public interface IOpenOrdersService {
    Paging<OpenOrdersVo> queryPage(PageRequest<OrdersQueryBo> bo);

    OpenOrdersVo queryDetail(String orderId);

    OpenOrdersVo queryByTranId(String tranId);

    List<OpenOrdersVo> queryByUserName(String userName);

    List<OpenOrdersVo> queryList(OrdersQueryBo bo);

    List<OpenOrdersVo> queryByCustomerIdAndState(String customerId, OrderStateEnum state);
}