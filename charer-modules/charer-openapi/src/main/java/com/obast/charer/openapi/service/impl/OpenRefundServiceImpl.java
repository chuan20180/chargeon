package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IRefundData;
import com.obast.charer.model.refund.Refund;
import com.obast.charer.openapi.dto.vo.OpenRefundVo;
import com.obast.charer.openapi.service.IOpenRefundService;
import com.obast.charer.qo.RefundQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OpenRefundServiceImpl implements IOpenRefundService {

    @Autowired
    private IRefundData refundData;

    @Override
    public Paging<OpenRefundVo> queryPage(PageRequest<RefundQueryBo> pageRequest) {
        Paging<Refund> pageList = refundData.findPage(pageRequest);
        Paging<OpenRefundVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Refund recharge: pageList.getRows()) {
            newPageList.getRows().add(fillData(recharge));
        }
        return newPageList;
    }

    @Override
    public OpenRefundVo queryDetail(String id) {
        Refund recharge = refundData.findById(id);
        return fillData(recharge);
    }

    private OpenRefundVo fillData(Refund refund) {
        OpenRefundVo vo = MapstructUtils.convert(refund, OpenRefundVo.class);
        if(vo == null) {
            return null;
        }

        return vo;
    }
}
