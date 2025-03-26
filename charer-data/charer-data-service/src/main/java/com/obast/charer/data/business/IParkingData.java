package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.parking.Parking;
import com.obast.charer.qo.ParkingQueryBo;

public interface IParkingData extends ICommonData<Parking, String>, IJPACommonData<Parking, ParkingQueryBo, String> {

    Parking findByStationIdAndNo(String stationId, String no);

    Parking add(Parking parking);

    Parking update(Parking parking);
}
