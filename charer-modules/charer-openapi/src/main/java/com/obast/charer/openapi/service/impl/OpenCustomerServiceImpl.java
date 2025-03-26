package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.data.business.ITopupData;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.openapi.dto.vo.OpenCustomerVo;
import com.obast.charer.openapi.service.IOpenCustomerService;
import com.obast.charer.qo.CustomerQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class OpenCustomerServiceImpl implements IOpenCustomerService {

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private ITopupData topupData;


    @Override
    public Paging<OpenCustomerVo> queryPage(PageRequest<CustomerQueryBo> pageRequest) {
        Paging<Customer> pageList = customerData.findPage(pageRequest);
        Paging<OpenCustomerVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Customer recharge: pageList.getRows()) {
            newPageList.getRows().add(fillData(recharge));
        }
        return newPageList;
    }

    @Override
    public OpenCustomerVo queryDetail(String stationId) {
        Customer recharge = customerData.findById(stationId);
        return fillData(recharge);
    }

    private OpenCustomerVo fillData(Customer customer) {
        OpenCustomerVo vo = MapstructUtils.convert(customer, OpenCustomerVo.class);
        if(vo == null) {
            return null;
        }

        BigDecimal topupAmount = topupData.findTopupAmountByCustomerId(customer.getId());
        vo.setTopupAmount(topupAmount);

        return vo;
    }
}
