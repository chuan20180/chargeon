package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.enums.InstantStateEnum;

import com.obast.charer.model.Instant;
import com.obast.charer.qo.InstantQueryBo;

import java.math.BigDecimal;
import java.util.List;


public interface IInstantData extends ICommonData<Instant, String>, IJPACommonData<Instant, InstantQueryBo, String> {
    Instant findByTranId(String id);

    Instant add(Instant topup);

    Instant update(Instant topup);

}
