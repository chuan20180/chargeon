package com.obast.charer.openapi.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import com.obast.charer.openapi.dto.bo.InstantBo;
import com.obast.charer.openapi.dto.vo.OpenInstantVo;
import com.obast.charer.qo.InstantQueryBo;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充值记录服务接口
 */
public interface IOpenInstantService {
    Paging<OpenInstantVo> queryPage(PageRequest<InstantQueryBo> bo);

    OpenInstantVo queryDetail(String id);

    OpenInstantVo queryDetailByTranId(String tranId);


    boolean addInstant(InstantBo data);

}
