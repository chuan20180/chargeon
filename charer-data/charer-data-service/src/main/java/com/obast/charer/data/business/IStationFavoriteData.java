package com.obast.charer.data.business;

import com.obast.charer.qo.StationFavoriteQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.station.StationFavorite;

public interface IStationFavoriteData extends ICommonData<StationFavorite, String>, IJPACommonData<StationFavorite, StationFavoriteQueryBo, String> {


}
