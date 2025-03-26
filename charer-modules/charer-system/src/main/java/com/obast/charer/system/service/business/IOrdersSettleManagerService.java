package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.vo.orders.OrdersSettleVo;
import com.obast.charer.qo.OrdersSettleQueryBo;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：订单结算管理服务接口
 */
public interface IOrdersSettleManagerService {
    Paging<OrdersSettleVo> queryPageList(PageRequest<OrdersSettleQueryBo> pageRequest);

    List<OrdersSettleVo> queryList(PageRequest<OrdersSettleQueryBo> pageRequest);

    OrdersSettleVo queryDetail(String stationId);

}
