package com.obast.charer.openapi.service;

import com.obast.charer.payment.core.PaymentResult;
import com.obast.charer.qo.PrePayQueryBo;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩订单服务接口
 */
public interface IOpenPayService {
    PaymentResult prepay(PrePayQueryBo bo);
}
