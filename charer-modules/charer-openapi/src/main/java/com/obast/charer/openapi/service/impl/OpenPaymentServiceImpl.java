package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.platform.IPaymentData;
import com.obast.charer.model.payment.Payment;
import com.obast.charer.openapi.dto.vo.OpenPaymentVo;
import com.obast.charer.openapi.service.IOpenPaymentService;
import com.obast.charer.qo.PaymentQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenPaymentServiceImpl implements IOpenPaymentService {

    @Autowired
    private IPaymentData paymentData;

    @Override
    public Paging<OpenPaymentVo> queryPage(PageRequest<PaymentQueryBo> pageRequest) {
        Paging<Payment> pageList = paymentData.findPage(pageRequest);
        Paging<OpenPaymentVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Payment recharge: pageList.getRows()) {
            newPageList.getRows().add(fillData(recharge));
        }
        return newPageList;
    }

    @Override
    public List<OpenPaymentVo> queryList(PaymentQueryBo bo) {
        List<Payment> list = paymentData.findList(bo);
        List<OpenPaymentVo> newList = new ArrayList<>();
        for(Payment recharge: list) {
            newList.add(fillData(recharge));
        }
        return newList;
    }

    @Override
    public OpenPaymentVo queryDetail(String stationId) {
        Payment recharge = paymentData.findById(stationId);
        return fillData(recharge);
    }

    private OpenPaymentVo fillData(Payment payment) {
        return MapstructUtils.convert(payment, OpenPaymentVo.class);
    }
}
