package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IRefundBalanceData;
import com.obast.charer.qo.RefundBalanceQueryBo;
import com.obast.charer.system.dto.vo.refund.RefundBalanceVo;
import com.obast.charer.system.service.business.IRefundBalanceManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：退款记录服务实现
 */
@Slf4j
@Service
public class RefundBalanceManagerServiceImpl implements IRefundBalanceManagerService {


    @Autowired
    private IRefundBalanceData refundBalanceData;


    @Override
    public Paging<RefundBalanceVo> queryPageList(PageRequest<RefundBalanceQueryBo> pageRequest) {
        return MapstructUtils.convert(refundBalanceData.findPage(pageRequest), RefundBalanceVo.class);
    }

    @Override
    public List<RefundBalanceVo> queryList(PageRequest<RefundBalanceQueryBo> pageRequest) {
        return MapstructUtils.convert(refundBalanceData.findList(pageRequest.getData()), RefundBalanceVo.class);
    }

    @Override
    public RefundBalanceVo queryDetail(String id) {
        return MapstructUtils.convert(refundBalanceData.findById(id), RefundBalanceVo.class);
    }
}
