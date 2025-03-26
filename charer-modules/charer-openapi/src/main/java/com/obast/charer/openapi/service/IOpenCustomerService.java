package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.vo.OpenCustomerVo;
import com.obast.charer.qo.CustomerQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI客户服务接口
 */
public interface IOpenCustomerService {
    Paging<OpenCustomerVo> queryPage(PageRequest<CustomerQueryBo> bo);

    OpenCustomerVo queryDetail(String id);
}
