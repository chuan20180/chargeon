package com.obast.charer.system.service.business.impl;

import com.obast.charer.system.dto.bo.StationFavoriteBo;
import com.obast.charer.system.dto.vo.station.StationFavoriteVo;
import com.obast.charer.qo.StationFavoriteQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IStationFavoriteData;
import com.obast.charer.system.service.business.IStationFavoriteManagerService;
import com.obast.charer.model.station.StationFavorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：场站管理服务实现
 */
@Service
public class StationFavoriteManagerServiceImpl implements IStationFavoriteManagerService {

    @Autowired
    private IStationFavoriteData stationFavoriteData;

    @Override
    public Paging<StationFavoriteVo> queryPageList(PageRequest<StationFavoriteQueryBo> pageRequest) {
        return MapstructUtils.convert(stationFavoriteData.findPage(pageRequest), StationFavoriteVo.class);
    }

    @Override
    public List<StationFavoriteVo> queryList(PageRequest<StationFavoriteQueryBo> pageRequest) {
        return MapstructUtils.convert(stationFavoriteData.findList(pageRequest.getData()), StationFavoriteVo.class);
    }

    @Override
    public StationFavoriteVo queryDetail(String id) {
        return MapstructUtils.convert(stationFavoriteData.findById(id), StationFavoriteVo.class);
    }

    @Override
    public boolean addStationFavorite(StationFavoriteBo bo) {
        StationFavorite di = bo.to(StationFavorite.class);
        return stationFavoriteData.save(di) != null;
    }

    @Override
    public boolean updateStationFavorite(StationFavoriteBo bo) {
        StationFavorite di = bo.to(StationFavorite.class);
        return stationFavoriteData.save(di) != null;
    }

    @Override
    public boolean deleteStationFavorite(String id) {
        id = queryDetail(id).getId();
        stationFavoriteData.deleteById(id);
        return true;
    }

    @Override
    public boolean batchDeleteStationFavorite(List<String> ids) {
        for(String stationId: ids) {
            stationFavoriteData.deleteById(stationId);
        }
        return true;
    }
}