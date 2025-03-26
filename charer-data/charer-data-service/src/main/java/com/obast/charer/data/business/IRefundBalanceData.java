package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;

import com.obast.charer.enums.TopupSourceEnum;
import com.obast.charer.model.refund.RefundBalance;
import com.obast.charer.qo.RefundBalanceQueryBo;

import java.math.BigDecimal;
import java.util.List;

public interface IRefundBalanceData extends ICommonData<RefundBalance, String>, IJPACommonData<RefundBalance, RefundBalanceQueryBo, String> {

    BigDecimal findRefundedAmountByCustomer(String customerId);

    List<RefundBalance> findListByRefundId(String refundId);

    RefundBalance findByTranId(String tranId);

    RefundBalance add(RefundBalance customerBalance);


}
