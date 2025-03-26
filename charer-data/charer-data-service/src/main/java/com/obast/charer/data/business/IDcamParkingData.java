package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.device.DcamParking;
import com.obast.charer.qo.DcamParkingQueryBo;

import java.util.List;

public interface IDcamParkingData extends ICommonData<DcamParking, String>, IJPACommonData<DcamParking, DcamParkingQueryBo, String> {

    List<DcamParking> findByDcamId(String dcamId);

    List<DcamParking> findByParkingId(String parkingId);

    DcamParking findByDcamIdAndName(String dcmId, String name);

    DcamParking add(DcamParking station);

    DcamParking update(DcamParking station);
}
