package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.OrdersQueryBo;
import com.obast.charer.system.dto.vo.orders.OrdersVo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：充电订单管理服务接口
 */
public interface IOrdersManagerService {
    Paging<OrdersVo> queryPageList(PageRequest<OrdersQueryBo> pageRequest);

    List<OrdersVo> queryList(PageRequest<OrdersQueryBo> pageRequest);

    OrdersVo queryDetail(String chargerOrderId);

    boolean deleteOrder(String id);

    boolean batchDeleteOrder(List<String> ids);

}
