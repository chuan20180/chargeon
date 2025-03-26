package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IOrdersSettleData;
import com.obast.charer.system.dto.vo.orders.OrdersSettleVo;
import com.obast.charer.system.service.business.IOrdersSettleManagerService;
import com.obast.charer.model.order.OrdersSettle;
import com.obast.charer.qo.OrdersSettleQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：订单结算管理服务实现
 */
@Service
public class OrdersSettleManagerServiceImpl implements IOrdersSettleManagerService {

    @Autowired
    private IOrdersSettleData stationData;

    @Override
    public Paging<OrdersSettleVo> queryPageList(PageRequest<OrdersSettleQueryBo> pageRequest) {
        Paging<OrdersSettle> pageList = stationData.findPage(pageRequest);
        Paging<OrdersSettleVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(OrdersSettle station: pageList.getRows()) {
            newPageList.getRows().add(fillData(station));
        }
        return newPageList;
    }

    @Override
    public List<OrdersSettleVo> queryList(PageRequest<OrdersSettleQueryBo> pageRequest) {
        List<OrdersSettle> list = stationData.findList(pageRequest.getData());
        List<OrdersSettleVo> newList = new ArrayList<>();
        for(OrdersSettle station: list) {
            newList.add(fillData(station));
        }
        return newList;
    }

    @Override
    public OrdersSettleVo queryDetail(String stationId) {
        return fillData(stationData.findById(stationId));
    }

    private OrdersSettleVo fillData(OrdersSettle station) {
        return MapstructUtils.convert(station, OrdersSettleVo.class);
    }
}