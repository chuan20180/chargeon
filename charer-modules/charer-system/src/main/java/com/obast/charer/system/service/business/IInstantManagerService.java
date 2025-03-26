package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.model.PaymentNotify;

import com.obast.charer.model.Instant;
import com.obast.charer.qo.InstantQueryBo;
import com.obast.charer.system.dto.bo.InstantBo;
import com.obast.charer.system.dto.vo.InstantVo;


import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值记录服务接口
 */
public interface IInstantManagerService {
    Paging<InstantVo> queryPageList(PageRequest<InstantQueryBo> pageRequest);

    List<InstantVo> queryList(PageRequest<InstantQueryBo> pageRequest);

    InstantVo queryDetail(String topupId);

    boolean removeInstant(String id);

    boolean batchRemoveInstant(List<String> ids);


    Instant getDetail(String id);

    void complete(PaymentNotify paymentNotify);



}
