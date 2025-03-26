package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.device.Camera;
import com.obast.charer.qo.CameraQueryBo;

import java.util.List;

public interface ICameraData extends ICommonData<Camera, String>, IJPACommonData<Camera, CameraQueryBo, String> {

    void initData();

    Camera findByDn(String dn);

    List<Camera> findByStationId(String stationId);

    Camera add(Camera Camera);

    Camera update(Camera Camera);
}
