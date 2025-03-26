package com.obast.charer.data.business;

import com.obast.charer.qo.ChargerGunQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.device.ChargerGun;

import java.util.List;

public interface IChargerGunData extends ICommonData<ChargerGun, String>, IJPACommonData<ChargerGun, ChargerGunQueryBo, String> {

    void initData();

    List<ChargerGun> findByChargerId(String chargerId);

    boolean checkParkingUnique(ChargerGun chargerGun);

    List<ChargerGun> findByStationId(String stationId);


    ChargerGun findByParkingId(String parkingId);

    ChargerGun findByChargerIdAndGunNo(String chargerId, String gunNo);

    ChargerGun add(ChargerGun chargerGun);

    ChargerGun update(ChargerGun chargerGun);


}
