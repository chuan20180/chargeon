package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ICustomerNotifyData;
import com.obast.charer.model.customer.CustomerNotify;
import com.obast.charer.openapi.dto.vo.OpenCustomerNotifyVo;
import com.obast.charer.openapi.service.IOpenCustomerNotifyService;
import com.obast.charer.qo.CustomerNotifyQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenCustomerNotifyServiceImpl implements IOpenCustomerNotifyService {

    @Autowired
    private ICustomerNotifyData customerNotifyData;

    @Override
    public Paging<OpenCustomerNotifyVo> queryPage(PageRequest<CustomerNotifyQueryBo> pageRequest) {
        Paging<CustomerNotify> pageList = customerNotifyData.findPage(pageRequest);
        Paging<OpenCustomerNotifyVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(CustomerNotify station: pageList.getRows()) {
            newPageList.getRows().add(fillData(station));
        }
        return newPageList;
    }

    @Override
    public OpenCustomerNotifyVo queryDetail(String stationId) {
        CustomerNotify charger = customerNotifyData.findById(stationId);
        return fillData(charger);
    }

    @Override
    public List<OpenCustomerNotifyVo> queryByCustomerId(String customerId) {
        List<CustomerNotify> list = customerNotifyData.findByCustomerId(customerId);
        List<OpenCustomerNotifyVo> newList = new ArrayList<>();
        for(CustomerNotify customerNotify: list) {
            newList.add(fillData(customerNotify));
        }
        return newList;
    }

    private OpenCustomerNotifyVo fillData(CustomerNotify customerNotify) {
        return MapstructUtils.convert(customerNotify, OpenCustomerNotifyVo.class);
    }
}
