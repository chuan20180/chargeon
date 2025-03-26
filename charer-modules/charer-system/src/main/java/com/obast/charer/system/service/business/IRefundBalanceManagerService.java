package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.RefundBalanceQueryBo;
import com.obast.charer.system.dto.vo.refund.RefundBalanceVo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：退款记录服务接口
 */
public interface IRefundBalanceManagerService {
    Paging<RefundBalanceVo> queryPageList(PageRequest<RefundBalanceQueryBo> pageRequest);

    List<RefundBalanceVo> queryList(PageRequest<RefundBalanceQueryBo> pageRequest);

    RefundBalanceVo queryDetail(String id);
}
