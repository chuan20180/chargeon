package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IOrdersData;
import com.obast.charer.enums.OrderStateEnum;
import com.obast.charer.model.order.Orders;
import com.obast.charer.qo.OrdersQueryBo;
import com.obast.charer.system.dto.vo.orders.OrdersVo;
import com.obast.charer.system.service.business.IOrdersManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电订单管理服务实现
 */
@Service
public class OrdersManagerServiceImpl implements IOrdersManagerService {

    @Autowired
    private IOrdersData ordersData;

    @Override
    public Paging<OrdersVo> queryPageList(PageRequest<OrdersQueryBo> pageRequest) {
        Paging<Orders> pageList = ordersData.findPage(pageRequest);
        Paging<OrdersVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Orders orders: pageList.getRows()) {
            newPageList.getRows().add(fillData(orders));
        }
        return newPageList;
    }

    @Override
    public List<OrdersVo> queryList(PageRequest<OrdersQueryBo> pageRequest) {
        List<Orders> list = ordersData.findList(pageRequest.getData());
        List<OrdersVo> newList = new ArrayList<>();
        for(Orders station: list) {
            newList.add(fillData(station));
        }
        return newList;
    }

    @Override
    public OrdersVo queryDetail(String chargerOrderId) {
        return fillData(ordersData.findById(chargerOrderId));
    }

    private OrdersVo fillData(Orders orders) {
        return MapstructUtils.convert(orders, OrdersVo.class);
    }

    @Override
    public boolean deleteOrder(String chargerOrderId) {
        ordersData.deleteById(chargerOrderId);
        return true;
    }

    @Override
    public boolean batchDeleteOrder(List<String> ids) {
        for(String id: ids) {
            ordersData.deleteById(id);
        }
        return true;
    }
}
