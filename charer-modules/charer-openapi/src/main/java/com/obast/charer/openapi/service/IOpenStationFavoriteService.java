package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.vo.OpenStationFavoriteVo;
import com.obast.charer.qo.StationFavoriteQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;


/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩服务接口
 */
public interface IOpenStationFavoriteService {
    Paging<OpenStationFavoriteVo> queryPage(PageRequest<StationFavoriteQueryBo> bo);

    OpenStationFavoriteVo queryDetail(String id);

}
