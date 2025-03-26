package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.device.Pmsc;
import com.obast.charer.qo.PmscQueryBo;

import java.util.List;

public interface IPmscData extends ICommonData<Pmsc, String>, IJPACommonData<Pmsc, PmscQueryBo, String> {

    void initData();

    Pmsc findByDn(String dn);

    List<Pmsc> findByStationId(String stationId);

    Pmsc add(Pmsc Pmsc);

    Pmsc update(Pmsc Pmsc);
}
