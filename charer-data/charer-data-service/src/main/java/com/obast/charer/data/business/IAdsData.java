package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.ads.Ads;
import com.obast.charer.qo.AdsQueryBo;

public interface IAdsData extends ICommonData<Ads, String>, IJPACommonData<Ads, AdsQueryBo, String> {

    Ads add(Ads ads);

    Ads update(Ads ads);
}
