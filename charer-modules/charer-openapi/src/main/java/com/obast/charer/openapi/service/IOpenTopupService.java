package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.bo.TopupBo;
import com.obast.charer.openapi.dto.vo.OpenTopupVo;
import com.obast.charer.qo.TopupQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充值记录服务接口
 */
public interface IOpenTopupService {
    Paging<OpenTopupVo> queryPage(PageRequest<TopupQueryBo> bo);

    OpenTopupVo queryDetail(String id);

    OpenTopupVo queryDetailByTranId(String tranId);


    boolean addTopup(TopupBo data);

}
