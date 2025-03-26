package com.obast.charer.system.service.business.impl;

import com.obast.charer.system.dto.bo.PaymentBo;
import com.obast.charer.system.dto.vo.payment.PaymentVo;
import com.obast.charer.system.service.system.IPaymentManagerService;
import com.obast.charer.qo.PaymentQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.platform.IPaymentData;
import com.obast.charer.model.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：场站管理服务实现
 */
@Service
public class PaymentManagerServiceImpl implements IPaymentManagerService {

    @Autowired
    private IPaymentData paymentData;

    @Override
    public Paging<PaymentVo> queryPageList(PageRequest<PaymentQueryBo> pageRequest) {
        return MapstructUtils.convert(paymentData.findPage(pageRequest), PaymentVo.class);
    }

    @Override
    public List<PaymentVo> queryList(PageRequest<PaymentQueryBo> pageRequest) {
        return MapstructUtils.convert(paymentData.findList(pageRequest.getData()), PaymentVo.class);
    }

    @Override
    public PaymentVo queryDetail(String stationId) {
        return MapstructUtils.convert(paymentData.findById(stationId), PaymentVo.class);
    }

    @Override
    public boolean addPayment(PaymentBo bo) {
        Payment di = bo.to(Payment.class);
        return paymentData.save(di) != null;
    }

    @Override
    public boolean updatePayment(PaymentBo bo) {
        Payment di = bo.to(Payment.class);
        return paymentData.save(di) != null;
    }

    @Override
    public void updateStatus(PaymentBo bo) {
        Payment payment = paymentData.findById(bo.getId());
        payment.setStatus(bo.getStatus());
        paymentData.save(payment);
    }

    @Override
    public void updateProperties(PaymentBo bo) {
        Payment payment = paymentData.findById(bo.getId());
        payment.setProperties(bo.getProperties());
        paymentData.save(payment);
    }

    @Override
    public boolean deletePayment(String paymentId) {
        paymentId = queryDetail(paymentId).getId();
        paymentData.deleteById(paymentId);
        return true;
    }

    @Override
    public boolean batchDeletePayment(List<String> ids) {
        for(String stationId: ids) {
            paymentData.deleteById(stationId);
        }
        return true;
    }
}