package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.data.business.IOrdersSettleData;
import com.obast.charer.data.business.IRefundBalanceData;
import com.obast.charer.data.business.ITopupData;
import com.obast.charer.enums.TopupSourceEnum;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.topup.Topup;
import com.obast.charer.qo.CustomerQueryBo;
import com.obast.charer.system.dto.vo.customer.CustomerVo;
import com.obast.charer.system.service.business.ICustomerManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerManagerServiceImpl implements ICustomerManagerService {

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private ITopupData topupData;

    @Autowired
    private IOrdersSettleData ordersSettleData;

    @Autowired
    private IRefundBalanceData refundBalanceData;

    @Override
    public Paging<CustomerVo> queryPageList(PageRequest<CustomerQueryBo> pageRequest) {
        Paging<Customer> pageList = customerData.findPage(pageRequest);
        Paging<CustomerVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Customer recharge: pageList.getRows()) {
            newPageList.getRows().add(fillData(recharge));
        }
        return newPageList;
    }

    @Override
    public List<CustomerVo> queryList(PageRequest<CustomerQueryBo> pageRequest) {
        List<Customer> list = customerData.findList(pageRequest.getData());
        List<CustomerVo> newList = new ArrayList<>();
        for(Customer recharge: list) {
            newList.add(fillData(recharge));
        }
        return newList;
    }

    @Override
    public CustomerVo queryDetail(String id) {
        Customer customer = customerData.findById(id);
        return fillData(customer);
    }

    @Override
    public boolean deleteCustomer(String id) {
        id = queryDetail(id).getId();
        customerData.deleteById(id);
        return true;
    }

    @Override
    public boolean batchDeleteCustomer(List<String> ids) {
        customerData.deleteByIds(ids);
        return true;
    }

    private CustomerVo fillData(Customer customer) {
        CustomerVo vo = customer.to(CustomerVo.class);
        if(vo == null) {
            return null;
        }

        BigDecimal topupAmount = topupData.findTopupAmountByCustomerId(customer.getId());

        BigDecimal settleAmount = ordersSettleData.findAmountByCustomerId(customer.getId());
        BigDecimal refundedAmount = refundBalanceData.findRefundedAmountByCustomer(customer.getId());

        vo.setTopupAmount(topupAmount);
        vo.setSettleAmount(settleAmount);
        vo.setRefundedAmount(refundedAmount);


        BigDecimal availableRefundAmount = new BigDecimal(0);
        List<Topup> availableRefundTopups = topupData.findRefundableByCustomerId(customer.getId());
        for(Topup topup: availableRefundTopups) {
            availableRefundAmount = availableRefundAmount.add(topup.getPaidAmount().subtract(topup.getRefundedAmount()));
        }
        vo.setAvailableRefundAmount(availableRefundAmount);

        BigDecimal quotaTotalAmount = new BigDecimal(0);
        if(vo.getQuotaAmount() != null && !vo.getQuotaAmount().isEmpty()) {
            for(Customer.Quota quota: vo.getQuotaAmount()) {
                quotaTotalAmount = quotaTotalAmount.add(quota.getAmount());
            }
        }
        vo.setQuotaTotalAmount(quotaTotalAmount);

        return vo;
    }
}