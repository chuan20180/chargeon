package com.obast.charer.openapi.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.openapi.dto.vo.OpenPriceVo;
import com.obast.charer.qo.PriceQueryBo;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩服务接口
 */
public interface IOpenPriceService {
    Paging<OpenPriceVo> queryPage(PageRequest<PriceQueryBo> bo);

    OpenPriceVo queryDetail(String id);

}
