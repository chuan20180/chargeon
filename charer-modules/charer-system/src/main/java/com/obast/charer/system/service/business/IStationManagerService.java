package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.StationBo;
import com.obast.charer.system.dto.vo.station.StationVo;
import com.obast.charer.qo.StationQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：场站管理服务接口
 */
public interface IStationManagerService {
    Paging<StationVo> queryPageList(PageRequest<StationQueryBo> pageRequest);

    List<StationVo> queryList(PageRequest<StationQueryBo> pageRequest);

    StationVo queryDetail(String stationId);

    boolean addStation(StationBo data);

    boolean updateStation(StationBo data);

    void updateStatus(StationBo bo);

    boolean deleteStation(String id);

    boolean batchDeleteStation(List<String> ids);


}
