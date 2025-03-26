package com.obast.charer.data.business;

import com.obast.charer.qo.ChargerDirectiveQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.device.ChargerDirective;

import java.util.List;


public interface IChargerDirectiveData extends ICommonData<ChargerDirective, String>, IJPACommonData<ChargerDirective, ChargerDirectiveQueryBo, String> {
    ChargerDirective add(ChargerDirective chargerDirective);

    List<ChargerDirective> findByChargerDn(String dn);


     ChargerDirective findOne(ChargerDirectiveQueryBo queryBo);
}
