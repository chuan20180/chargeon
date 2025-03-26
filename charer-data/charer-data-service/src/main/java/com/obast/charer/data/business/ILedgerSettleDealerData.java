package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;

import com.obast.charer.model.ledger.LedgerSettleDealer;
import com.obast.charer.qo.LedgerSettleDealerQueryBo;

import java.util.List;

public interface ILedgerSettleDealerData extends ICommonData<LedgerSettleDealer, String>, IJPACommonData<LedgerSettleDealer, LedgerSettleDealerQueryBo, String> {

    List<LedgerSettleDealer> findByLedgerSettleId(String dealId);

    LedgerSettleDealer add(LedgerSettleDealer ledgerSettleDealer);
}
