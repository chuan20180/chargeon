package com.obast.charer.system.service.business;

import com.obast.charer.system.dto.bo.StationFavoriteBo;
import com.obast.charer.system.dto.vo.station.StationFavoriteVo;
import com.obast.charer.qo.StationFavoriteQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：场站收藏管理服务接口
 */
public interface IStationFavoriteManagerService {
    Paging<StationFavoriteVo> queryPageList(PageRequest<StationFavoriteQueryBo> pageRequest);

    List<StationFavoriteVo> queryList(PageRequest<StationFavoriteQueryBo> pageRequest);

    StationFavoriteVo queryDetail(String id);

    boolean addStationFavorite(StationFavoriteBo data);

    boolean updateStationFavorite(StationFavoriteBo data);


    boolean deleteStationFavorite(String id);

    boolean batchDeleteStationFavorite(List<String> ids);


}
