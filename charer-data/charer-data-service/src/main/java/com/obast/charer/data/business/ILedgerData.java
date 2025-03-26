package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.ledger.Ledger;
import com.obast.charer.qo.LedgerQueryBo;

public interface ILedgerData extends ICommonData<Ledger, String>, IJPACommonData<Ledger, LedgerQueryBo, String> {


    Ledger add(Ledger ledger);
}
