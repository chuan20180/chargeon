package com.obast.charer.data.business;

import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.enums.AppOsEnum;
import com.obast.charer.model.customer.CustomerLogin;
import com.obast.charer.model.station.Station;
import com.obast.charer.qo.CustomerLoginQueryBo;

import java.util.List;

public interface ICustomerLoginData extends ICommonData<CustomerLogin, String>, IJPACommonData<CustomerLogin, CustomerLoginQueryBo, String> {

    CustomerLogin findByCustomerIdAndPlatform(String customerId, PlatformTypeEnum platform);

    CustomerLogin findByDnAndPlatform(String dn, PlatformTypeEnum platform);

    List<CustomerLogin> findListByPlatform(AppOsEnum platform);

    List<CustomerLogin> findListByPlatforms(List<AppOsEnum> platforms);

    CustomerLogin add(CustomerLogin customerLogin);

    CustomerLogin update(CustomerLogin customerLogin);
}
