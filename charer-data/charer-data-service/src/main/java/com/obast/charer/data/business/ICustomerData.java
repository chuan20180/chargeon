package com.obast.charer.data.business;

import com.obast.charer.enums.CustomerTypeEnum;
import com.obast.charer.qo.CustomerQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.customer.Customer;

import java.util.List;

public interface ICustomerData extends ICommonData<Customer, String>, IJPACommonData<Customer, CustomerQueryBo, String> {

    Customer add(Customer customer);

    Customer update(Customer customer);

    boolean checkUserNameUnique(CustomerQueryBo bo);

    Customer findByOpenId(String openId);

    Customer findByUserName(String userName);

    Customer findByMobile(String mobile);

    List<Customer> findListByType(CustomerTypeEnum typeEnum);
}
