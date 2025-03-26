package com.obast.charer.data.business;

import com.obast.charer.qo.RefundQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.refund.Refund;


public interface IRefundData extends ICommonData<Refund, String>, IJPACommonData<Refund, RefundQueryBo, String> {
    Refund add(Refund refund);

    Refund findByTranId(String id);
}
