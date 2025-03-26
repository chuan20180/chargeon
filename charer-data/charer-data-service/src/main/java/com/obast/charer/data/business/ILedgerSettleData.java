package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;

import com.obast.charer.model.ledger.LedgerSettle;
import com.obast.charer.qo.LedgerSettleQueryBo;


public interface ILedgerSettleData extends ICommonData<LedgerSettle, String>, IJPACommonData<LedgerSettle, LedgerSettleQueryBo, String> {


    LedgerSettle add(LedgerSettle ledgerSettle);
}
