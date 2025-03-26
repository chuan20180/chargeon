package com.obast.charer.openapi.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.openapi.dto.vo.OpenCustomerNotifyVo;
import com.obast.charer.qo.CustomerNotifyQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩服务接口
 */
public interface IOpenCustomerNotifyService {
    Paging<OpenCustomerNotifyVo> queryPage(PageRequest<CustomerNotifyQueryBo> bo);

    OpenCustomerNotifyVo queryDetail(String stationId);

    List<OpenCustomerNotifyVo> queryByCustomerId(String customerId);

}
