package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.CustomerQueryBo;
import com.obast.charer.system.dto.vo.customer.CustomerVo;

import java.util.List;

public interface ICustomerManagerService {
    Paging<CustomerVo> queryPageList(PageRequest<CustomerQueryBo> pageRequest);

    List<CustomerVo> queryList(PageRequest<CustomerQueryBo> pageRequest);

    CustomerVo queryDetail(String customerId);

    boolean deleteCustomer(String customerId);

    boolean batchDeleteCustomer(List<String> ids);


}
