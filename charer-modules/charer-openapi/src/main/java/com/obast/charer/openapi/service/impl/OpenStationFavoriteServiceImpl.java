package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IStationFavoriteData;
import com.obast.charer.model.station.StationFavorite;
import com.obast.charer.openapi.dto.vo.OpenStationFavoriteVo;
import com.obast.charer.openapi.service.IOpenStationFavoriteService;
import com.obast.charer.qo.StationFavoriteQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OpenStationFavoriteServiceImpl implements IOpenStationFavoriteService {

    @Autowired
    private IStationFavoriteData stationFavoriteData;

    @Override
    public Paging<OpenStationFavoriteVo> queryPage(PageRequest<StationFavoriteQueryBo> pageRequest) {
        Paging<StationFavorite> pageList = stationFavoriteData.findPage(pageRequest);
        Paging<OpenStationFavoriteVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(StationFavorite stationFavorite: pageList.getRows()) {
            newPageList.getRows().add(fillData(stationFavorite));
        }
        return newPageList;
    }

    @Override
    public OpenStationFavoriteVo queryDetail(String id) {
        StationFavorite stationFavorite = stationFavoriteData.findById(id);
        return fillData(stationFavorite);
    }

    private OpenStationFavoriteVo fillData(StationFavorite stationFavorite) {
        OpenStationFavoriteVo vo = MapstructUtils.convert(stationFavorite, OpenStationFavoriteVo.class);
        if(vo == null) {
            return null;
        }

        return vo;
    }
}