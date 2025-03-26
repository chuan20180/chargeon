package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.station.Station;
import com.obast.charer.qo.StationQueryBo;

import java.util.List;

public interface IStationData extends ICommonData<Station, String>, IJPACommonData<Station, StationQueryBo, String> {

    List<Station> findByAgentId(String dealId);

    List<Station> findNoAgentList(StationQueryBo queryBo);

    Station add(Station station);

    Station update(Station station);
}
