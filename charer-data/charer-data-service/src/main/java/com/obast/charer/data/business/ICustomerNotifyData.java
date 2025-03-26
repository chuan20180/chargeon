package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;

import com.obast.charer.model.customer.CustomerNotify;
import com.obast.charer.qo.CustomerNotifyQueryBo;

import java.util.List;

public interface ICustomerNotifyData extends ICommonData<CustomerNotify, String>, IJPACommonData<CustomerNotify, CustomerNotifyQueryBo, String> {


    List<CustomerNotify> findByCustomerId(String customerId);

    CustomerNotify add(CustomerNotify charger);

}
