package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.*;
import com.obast.charer.data.system.ISysAgentData;
import com.obast.charer.data.system.ISysAgentStationData;
import com.obast.charer.data.system.ISysAgentStationDealerData;
import com.obast.charer.model.device.*;
import com.obast.charer.system.dto.bo.SysAgentStationBo;
import com.obast.charer.system.dto.vo.SysAgentStationVo;
import com.obast.charer.system.dto.vo.station.StationVo;
import com.obast.charer.system.service.system.ISysAgentStationService;
import com.obast.charer.model.station.Station;
import com.obast.charer.model.system.SysAgent;
import com.obast.charer.model.system.SysAgentStation;
import com.obast.charer.model.system.SysAgentStationDealer;
import com.obast.charer.qo.SysAgentStationQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：租户分帐管理服务实现
 */
@Service
public class SysAgentStationServiceImpl implements ISysAgentStationService {

    @Autowired
    private ISysAgentStationDealerData sysAgentStationDealerData;

    @Autowired
    private ISysAgentStationData sysAgentStationData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private ISysAgentData sysAgentData;

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private IChargerGunData chargerGunData;

    @Autowired
    private IDcamData dcamData;

    @Autowired
    private IPmscData pmscData;

    @Autowired
    private ICameraData cameraData;

    @Override
    public Paging<SysAgentStationVo> queryPageList(PageRequest<SysAgentStationQueryBo> pageRequest) {
        Paging<SysAgentStation> pageList = sysAgentStationData.findPage(pageRequest);
        Paging<SysAgentStationVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(SysAgentStation sysAgentStation: pageList.getRows()) {
            newPageList.getRows().add(fillData(sysAgentStation));
        }
        return newPageList;
    }

    @Override
    public List<SysAgentStationVo> queryList(PageRequest<SysAgentStationQueryBo> pageRequest) {
        List<SysAgentStation> list = sysAgentStationData.findList(pageRequest.getData());
        List<SysAgentStationVo> newList = new ArrayList<>();
        for(SysAgentStation sysAgentStation: list) {
            newList.add(fillData(sysAgentStation));
        }
        return newList;
    }

    @Override
    public SysAgentStationVo queryDetail(String id) {
        return fillData(sysAgentStationData.findById(id));
    }

    private SysAgentStationVo fillData(SysAgentStation sysAgentStation) {
        SysAgentStationVo vo = MapstructUtils.convert(sysAgentStation, SysAgentStationVo.class);
        if(vo == null) {
            return null;
        }
        vo.setStation(MapstructUtils.convert(stationData.findById(vo.getStationId()), StationVo.class));
        return vo;
    }

    @Override
    @Transactional
    public boolean add(SysAgentStationBo bo) {
        Station station = stationData.findById(bo.getStationId());
        if(station == null) {
            throw new BizException(ErrCode.STATION_NOT_FOUND);
        }

        if(StringUtils.isNoneBlank(station.getAgentId())) {
            throw new BizException(ErrCode.SYS_AGENT_STATION_HAS_BIND);
        }

        SysAgent sysAgent = sysAgentData.findById(bo.getAgentId());
        if(sysAgent == null) {
            throw new BizException(ErrCode.SYS_AGENT_NOT_FOUND);
        }

        SysAgentStation di = bo.to(SysAgentStation.class);
        sysAgentStationData.add(di);

        //更新设备的agentId
        List<Charger> chargers = chargerData.findByStationId(station.getId());
        if(!chargers.isEmpty()) {
            for(Charger charger: chargers) {
                charger.setAgentId(sysAgent.getId());
                chargerData.update(charger);
            }
        }

        List<ChargerGun> chargerGuns = chargerGunData.findByStationId(station.getId());
        if(!chargerGuns.isEmpty()) {
            for(ChargerGun chargerGun: chargerGuns) {
                chargerGun.setAgentId(sysAgent.getId());
                chargerGunData.update(chargerGun);
            }
        }


        List<Dcam> dcams = dcamData.findByStationId(station.getId());
        if(!dcams.isEmpty()) {
            for(Dcam dcam: dcams) {
                dcam.setAgentId(sysAgent.getId());
                dcamData.update(dcam);
            }
        }
        List<Pmsc> pmscs = pmscData.findByStationId(station.getId());
        if(!pmscs.isEmpty()) {
            for(Pmsc pmsc: pmscs) {
                pmsc.setAgentId(sysAgent.getId());
                pmscData.update(pmsc);
            }
        }
        List<Camera> cameras = cameraData.findByStationId(station.getId());
        if(!cameras.isEmpty()) {
            for(Camera camera: cameras) {
                camera.setAgentId(sysAgent.getId());
                cameraData.update(camera);
            }
        }

        //更新场站的agentId
        station.setAgentId(sysAgent.getId());
        stationData.update(station);



        return true;
    }


    @Override
    public void updateStatus(SysAgentStationBo bo) {
        SysAgentStation sysAgentStation = sysAgentStationData.findById(bo.getId());
        sysAgentStation.setStatus(bo.getStatus());
        sysAgentStationData.save(sysAgentStation);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        SysAgentStation sysAgentStation = sysAgentStationData.findById(id);
        if(sysAgentStation == null) {
            throw new BizException(ErrCode.SYS_AGENT_STATION_NOT_FOUND);
        }

        Station station = stationData.findById(sysAgentStation.getStationId());
        if(station == null) {
            throw new BizException(ErrCode.STATION_NOT_FOUND);
        }

        //更新设备的agentId
        List<Charger> chargers = chargerData.findByStationId(station.getId());
        if(!chargers.isEmpty()) {
            for(Charger charger: chargers) {
                charger.setAgentId(null);
                chargerData.update(charger);
            }
        }
        List<ChargerGun> chargerGuns = chargerGunData.findByStationId(station.getId());
        if(!chargerGuns.isEmpty()) {
            for(ChargerGun chargerGun: chargerGuns) {
                chargerGun.setAgentId(null);
                chargerGunData.update(chargerGun);
            }
        }
        List<Dcam> dcams = dcamData.findByStationId(station.getId());
        if(!dcams.isEmpty()) {
            for(Dcam dcam: dcams) {
                dcam.setAgentId(null);
                dcamData.update(dcam);
            }
        }
        List<Pmsc> pmscs = pmscData.findByStationId(station.getId());
        if(!pmscs.isEmpty()) {
            for(Pmsc pmsc: pmscs) {
                pmsc.setAgentId(null);
                pmscData.update(pmsc);
            }
        }
        List<Camera> cameras = cameraData.findByStationId(station.getId());
        if(!cameras.isEmpty()) {
            for(Camera camera: cameras) {
                camera.setAgentId(null);
                cameraData.update(camera);
            }
        }

        //更新场站agentId
        station.setAgentId(null);
        stationData.update(station);

        List<SysAgentStationDealer> sysAgentStationDealers = sysAgentStationDealerData.findByAgentStationId(sysAgentStation.getId());
        if(!sysAgentStationDealers.isEmpty()) {
            List<String> sysAgentStationDealerIds = sysAgentStationDealers.stream().map(SysAgentStationDealer::getId).collect(Collectors.toList());
            if(!sysAgentStationDealerIds.isEmpty()) {
                sysAgentStationDealerData.deleteByIds(sysAgentStationDealerIds);
            }
        }

        sysAgentStationData.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean batchDelete(List<String> ids) {
        for(String id: ids) {
           delete(id);
        }
        return true;
    }
}