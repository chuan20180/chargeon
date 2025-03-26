package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.vo.OpenStationVo;
import com.obast.charer.qo.StationQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩服务接口
 */
public interface IOpenStationService {
    Paging<OpenStationVo> queryPage(PageRequest<StationQueryBo> bo);

    List<OpenStationVo> queryList(StationQueryBo bo);

    OpenStationVo queryDetail(String stationId);

}
