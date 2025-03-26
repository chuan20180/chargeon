package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.model.price.Price;
import com.obast.charer.model.station.Station;
import com.obast.charer.system.dto.bo.ChargerBo;
import com.obast.charer.system.dto.vo.device.ChargerGunVo;
import com.obast.charer.system.dto.vo.device.ChargerVo;
import com.obast.charer.qo.DeviceLogQueryBo;
import com.obast.charer.system.service.business.IChargerManagerService;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.device.ChargerDirective;
import com.obast.charer.qo.ChargerQueryBo;
import com.obast.charer.temporal.IThingModelMessageData;
import com.obast.charer.data.business.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩管理服务实现
 */
@Service
public class ChargerManagerServiceImpl implements IChargerManagerService {

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private IChargerGunData chargerGunData;

    @Autowired
    private IChargerDirectiveData chargerDirectiveData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private IPriceData priceData;

    @Lazy
    @Autowired
    private IThingModelMessageData thingModelMessageData;

    @Override
    public Paging<ChargerVo> queryPageList(PageRequest<ChargerQueryBo> pageRequest) {
        Paging<Charger> pageList = chargerData.findPage(pageRequest);
        Paging<ChargerVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Charger charger: pageList.getRows()) {
            newPageList.getRows().add(fillData(charger));
        }
        return newPageList;
    }

    @Override
    public List<ChargerVo> queryList(PageRequest<ChargerQueryBo> pageRequest) {
        List<Charger> list = chargerData.findList(pageRequest.getData());
        List<ChargerVo> chargerVos = new ArrayList<>();
        for(Charger charger: list) {
            chargerVos.add(fillData(charger));
        }
        return chargerVos;
    }

    @Override
    public ChargerVo queryDetail(String chargerId) {
        Charger charger = chargerData.findById(chargerId);
        return fillData(charger);
    }

    private ChargerVo fillData(Charger charger) {
        ChargerVo vo = MapstructUtils.convert(charger, ChargerVo.class);
        if(vo == null) {
            return null;
        }
        vo.setGuns(MapstructUtils.convert(chargerGunData.findByChargerId(vo.getId()), ChargerGunVo.class));

        return vo;
    }

    @Override
    @Transactional
    public void addCharger(ChargerBo bo) {
        Charger chargerRepetition = chargerData.findByDn(bo.getDn());
        if (chargerRepetition != null) {
            throw new BizException(ErrCode.CHARGER_DN_ALREADY);
        }

        Station station = stationData.findById(bo.getStationId());
        if (station == null) {
            throw new BizException(ErrCode.STATION_NOT_FOUND);
        }

        Price price = priceData.findById(bo.getPriceId());
        if (price == null) {
            throw new BizException(ErrCode.PRICE_NOT_FOUND);
        }

        Charger charger = bo.to(Charger.class);

        chargerData.add(charger);
    }

    @Override
    public void updateCharger(ChargerBo bo) {
        Charger di = bo.to(Charger.class);
        chargerData.update(di);
    }

    @Override
    public void updateStatus(ChargerBo bo) {
        Charger charger = chargerData.findById(bo.getId());
        charger.setStatus(bo.getStatus());
        chargerData.save(charger);
    }

    @Override
    @Transactional
    public boolean deleteCharger(String chargerId) {
        Charger charger = chargerData.findById(chargerId);
        if(charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        chargerData.deleteById(chargerId);

        List<ChargerGun> chargerGuns = chargerGunData.findByChargerId(charger.getId());
        if(!chargerGuns.isEmpty()) {
            List<String> chargerGunIds = chargerGuns.stream().map(ChargerGun::getId).collect(Collectors.toList());
            chargerGunData.deleteByIds(chargerGunIds);
        }

        List<ChargerDirective> chargerDirectives = chargerDirectiveData.findByChargerDn(charger.getDn());
        if(!chargerDirectives.isEmpty()) {
            List<String> chargerDirectiveIdss = chargerDirectives.stream().map(ChargerDirective::getId).collect(Collectors.toList());
            chargerDirectiveData.deleteByIds(chargerDirectiveIdss);
        }

        return true;
    }

    @Override
    public boolean batchDeleteCharger(List<String> ids) {
        for(String chargerId: ids) {
            chargerData.deleteById(chargerId);
        }
        return true;
    }

    @Override
    public Paging<ThingModelMessage> logs(PageRequest<DeviceLogQueryBo> request) {
        return thingModelMessageData.findPage(request);
    }
}
