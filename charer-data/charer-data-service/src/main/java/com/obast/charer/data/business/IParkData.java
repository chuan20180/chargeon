package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.park.Park;
import com.obast.charer.qo.ParkQueryBo;

public interface IParkData extends ICommonData<Park, String>, IJPACommonData<Park, ParkQueryBo, String> {

    Park findOnParkingByParkingId(String parkingId);

    Park add(Park station);

    Park update(Park station);
}
