package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.model.refund.Refund;
import com.obast.charer.common.model.RefundNotify;
import com.obast.charer.qo.RefundQueryBo;
import com.obast.charer.system.dto.bo.RefundBo;
import com.obast.charer.system.dto.vo.refund.RefundVo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：退款记录服务接口
 */
public interface IRefundManagerService {
    Paging<RefundVo> queryPageList(PageRequest<RefundQueryBo> pageRequest);

    List<RefundVo> queryList(PageRequest<RefundQueryBo> pageRequest);

    Refund queryDetail(String id);

    boolean addRefund(RefundBo data);

    boolean doRefund(String id);

    boolean removeRefund(String id);

    void complete(RefundNotify refundNotify);


}
