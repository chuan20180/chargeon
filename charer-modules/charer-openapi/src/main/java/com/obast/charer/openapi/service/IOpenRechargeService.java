package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.vo.OpenRechargeVo;
import com.obast.charer.qo.RechargeQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩服务接口
 */
public interface IOpenRechargeService {
    Paging<OpenRechargeVo> queryPage(PageRequest<RechargeQueryBo> request);

    List<OpenRechargeVo> queryList(RechargeQueryBo bo);

    OpenRechargeVo queryDetail(String id);

}
