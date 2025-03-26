package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.price.PricePark;
import com.obast.charer.qo.PriceParkQueryBo;

public interface IPriceParkData extends ICommonData<PricePark, String>, IJPACommonData<PricePark, PriceParkQueryBo, String> {

    PricePark add(PricePark price);

    PricePark update(PricePark price);


}
