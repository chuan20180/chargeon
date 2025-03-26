package com.obast.charer.data.business;

import com.obast.charer.model.promotion.Promotion;
import com.obast.charer.qo.RechargeQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.platform.Recharge;


public interface IRechargeData extends ICommonData<Recharge, String>, IJPACommonData<Recharge, RechargeQueryBo, String> {

    Recharge findByName(String name);

    Recharge add(Recharge entity);

    Recharge update(Recharge entity);
}
