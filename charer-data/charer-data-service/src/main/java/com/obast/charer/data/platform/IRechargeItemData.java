package com.obast.charer.data.platform;

import com.obast.charer.qo.RechargeItemQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.platform.RechargeItem;

import java.util.List;


public interface IRechargeItemData extends ICommonData<RechargeItem, String>, IJPACommonData<RechargeItem, RechargeItemQueryBo, String> {
    List<RechargeItem> findByRechargeId(String rechargeId);
}
