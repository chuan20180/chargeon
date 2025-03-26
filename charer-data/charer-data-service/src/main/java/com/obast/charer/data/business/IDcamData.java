package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.device.Dcam;
import com.obast.charer.qo.DcamQueryBo;

import java.util.List;

public interface IDcamData extends ICommonData<Dcam, String>, IJPACommonData<Dcam, DcamQueryBo, String> {

    void initData();

    Dcam findByDn(String dn);

    List<Dcam> findByStationId(String stationId);

    List<Dcam> findByPriceParkId(String priceId);

    Dcam add(Dcam Dcam);

    Dcam update(Dcam Dcam);
}
