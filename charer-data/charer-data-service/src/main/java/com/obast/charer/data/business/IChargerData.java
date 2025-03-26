package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.device.Charger;
import com.obast.charer.qo.ChargerQueryBo;

import java.util.List;

public interface IChargerData extends ICommonData<Charger, String>, IJPACommonData<Charger, ChargerQueryBo, String> {

    void initData();

    Long findCount(ChargerQueryBo queryBo);

    Charger findByDn(String dn);

    List<Charger> findByPriceId(String priceId);

    List<Charger> findByStationId(String stationId);
    List<Charger> findAvailableByStationId(String stationId);

    Charger add(Charger charger);

    Charger update(Charger charger);
}
