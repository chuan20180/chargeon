package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.vo.OpenRefundVo;
import com.obast.charer.qo.RefundQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI退款服务接口
 */
public interface IOpenRefundService {
    Paging<OpenRefundVo> queryPage(PageRequest<RefundQueryBo> bo);

    OpenRefundVo queryDetail(String id);
}
