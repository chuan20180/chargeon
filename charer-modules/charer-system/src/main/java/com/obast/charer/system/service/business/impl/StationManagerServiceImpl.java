package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.tenant.helper.DealerHelper;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.data.business.IStationData;
import com.obast.charer.system.dto.bo.StationBo;
import com.obast.charer.system.dto.vo.station.StationVo;
import com.obast.charer.system.service.business.IStationManagerService;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.station.Station;
import com.obast.charer.qo.StationQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：场站管理服务实现
 */
@Service
public class StationManagerServiceImpl implements IStationManagerService {

    @Autowired
    private IStationData stationData;


    @Autowired
    private IChargerData chargerData;


    @Override
    public Paging<StationVo> queryPageList(PageRequest<StationQueryBo> pageRequest) {
        Paging<Station> pageList = stationData.findPage(pageRequest);
        Paging<StationVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Station station: pageList.getRows()) {
            newPageList.getRows().add(fillData(station));
        }
        return newPageList;
    }

    @Override
    public List<StationVo> queryList(PageRequest<StationQueryBo> pageRequest) {
        List<Station> list = stationData.findList(pageRequest.getData());
        List<StationVo> newList = new ArrayList<>();
        for(Station station: list) {
            newList.add(fillData(station));
        }
        return newList;
    }

    @Override
    public List<StationVo> queryNoAgentList(PageRequest<StationQueryBo> pageRequest) {
        List<Station> list = DealerHelper.ignore(()->stationData.findNoAgentList(pageRequest.getData())) ;
        List<StationVo> newList = new ArrayList<>();
        for(Station station: list) {
            newList.add(fillData(station));
        }
        return newList;
    }

    @Override
    public StationVo queryDetail(String stationId) {
        return fillData(stationData.findById(stationId));
    }

    @Override
    public boolean addStation(StationBo bo) {
        Station di = bo.to(Station.class);
        Station.Locate locate = bo.getLocate();
        if(locate!= null) {
            di.setLatitude(locate.getLatitude());
            di.setLongitude(locate.getLongitude());
        }
        return stationData.add(di) != null;
    }

    @Override
    public boolean updateStation(StationBo bo) {
        Station di = bo.to(Station.class);
        Station.Locate locate = bo.getLocate();
        if(locate!= null) {
            di.setLatitude(locate.getLatitude());
            di.setLongitude(locate.getLongitude());
        }
        return stationData.update(di) != null;
    }


    @Override
    public void updateStatus(StationBo bo) {
        Station station = stationData.findById(bo.getId());
        station.setStatus(bo.getStatus());
        stationData.save(station);
    }

    @Override
    public boolean deleteStation(String stationId) {
        stationId = queryDetail(stationId).getId();
        //查看场站是否存在设备
        List<Charger> chargers = chargerData.findByStationId(stationId);
        if (!chargers.isEmpty()) {
            throw new BizException(ErrCode.STATION_EXISTS_CHARGER);
        }

        stationData.deleteById(stationId);
        return true;
    }

    @Override
    public boolean batchDeleteStation(List<String> ids) {
        for(String stationId: ids) {
            //查看场站是否存在设备
            List<Charger> chargers = chargerData.findByStationId(stationId);
            if (!chargers.isEmpty()) {
                throw new BizException(ErrCode.STATION_EXISTS_CHARGER);
            }
            stationData.deleteById(stationId);
        }
        return true;
    }

    private StationVo fillData(Station station) {
        StationVo vo = MapstructUtils.convert(station, StationVo.class);
        if(vo == null) {
            return null;
        }
        vo.setLocate(new Station.Locate(station.getLongitude(), station.getLatitude()));
        return vo;
    }
}