package com.obast.charer.system.service.system;

import com.obast.charer.qo.PaymentQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.PaymentBo;
import com.obast.charer.system.dto.vo.payment.PaymentVo;

import java.util.List;

public interface IPaymentManagerService {
    Paging<PaymentVo> queryPageList(PageRequest<PaymentQueryBo> pageRequest);

    List<PaymentVo> queryList(PageRequest<PaymentQueryBo> pageRequest);

    PaymentVo queryDetail(String stationId);

    boolean addPayment(PaymentBo data);

    boolean updatePayment(PaymentBo data);

    void updateStatus(PaymentBo bo);

    void updateProperties(PaymentBo bo);

    boolean deletePayment(String id);

    boolean batchDeletePayment(List<String> ids);
}
