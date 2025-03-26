package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IOrdersData;
import com.obast.charer.data.business.IOrdersSettleData;
import com.obast.charer.enums.OrderStateEnum;
import com.obast.charer.model.order.Orders;
import com.obast.charer.openapi.dto.vo.OpenOrdersSettleVo;
import com.obast.charer.openapi.dto.vo.OpenOrdersVo;
import com.obast.charer.openapi.service.IOpenOrdersService;
import com.obast.charer.qo.OrdersQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenOrdersServiceImpl implements IOpenOrdersService {

    @Autowired
    private IOrdersData orderData;

    @Autowired
    private IOrdersSettleData ordersSettleData;

    @Override
    public Paging<OpenOrdersVo> queryPage(PageRequest<OrdersQueryBo> pageRequest) {
        Paging<Orders> pageList = orderData.findPage(pageRequest);
        Paging<OpenOrdersVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Orders orders : pageList.getRows()) {
            newPageList.getRows().add(fillData(orders));
        }
        return newPageList;
    }

    @Override
    public List<OpenOrdersVo> queryByUserName(String userName) {
        List<Orders> list = orderData.findByUserName(userName);
        List<OpenOrdersVo> newList = new ArrayList<>();
        for(Orders orders : list) {
            newList.add(fillData(orders));
        }
        return newList;
    }

    @Override
    public List<OpenOrdersVo> queryList(OrdersQueryBo bo) {
        List<Orders> list = orderData.findList(bo);
        List<OpenOrdersVo> newList = new ArrayList<>();
        for(Orders orders : list) {
            newList.add(fillData(orders));
        }
        return newList;
    }

    @Override
    public List<OpenOrdersVo> queryByCustomerIdAndState(String customerId, OrderStateEnum state) {
        List<Orders> list = orderData.findByCustomerIdAndState(customerId, state);
        List<OpenOrdersVo> newList = new ArrayList<>();
        for(Orders orders : list) {
            newList.add(fillData(orders));
        }
        return newList;
    }

    @Override
    public OpenOrdersVo queryDetail(String id) {
        Orders order = orderData.findById(id);
        return fillData(order);
    }

    @Override
    public OpenOrdersVo queryByTranId(String tranId) {
        Orders order= orderData.findByTranId(tranId);
        return fillData(order);
    }

    private OpenOrdersVo fillData(Orders orders) {
        OpenOrdersVo vo = MapstructUtils.convert(orders, OpenOrdersVo.class);
        if(vo == null) {
            return null;
        }
        vo.setSettles(MapstructUtils.convert(ordersSettleData.findListByOrderId(vo.getId()), OpenOrdersSettleVo.class));

        if(vo.getState().equals(OrderStateEnum.Settled)) {
            BigDecimal orderAmount = vo.getTotalAmount().add(vo.getParkAmount());
            vo.setOrderAmount(orderAmount);
        }

        return vo;
    }
}
