package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.enums.TopupSourceEnum;
import com.obast.charer.enums.TopupStateEnum;
import com.obast.charer.model.topup.Topup;
import com.obast.charer.qo.TopupQueryBo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface ITopupData extends ICommonData<Topup, String>, IJPACommonData<Topup, TopupQueryBo, String> {

    Map<String, Object> findSummary();

    Map<String, Object> findSummaryByCreateTime(String startTime, String endTime);

    Topup findByTranId(String id);

    List<Topup> findByCustomerIdAndState(String customerId, TopupStateEnum state);

    List<Topup> findRefundableByCustomerId(String customerId);

    BigDecimal findTopupAmountByCustomerId(String customerId);



    Topup add(Topup topup);

    Topup update(Topup topup);

}
