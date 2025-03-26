package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.vo.OpenPaymentVo;
import com.obast.charer.qo.PaymentQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import java.util.List;


/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩服务接口
 */
public interface IOpenPaymentService {
    Paging<OpenPaymentVo> queryPage(PageRequest<PaymentQueryBo> bo);

    List<OpenPaymentVo> queryList(PaymentQueryBo bo);

    OpenPaymentVo queryDetail(String id);
}
