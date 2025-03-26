package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.model.device.Charger;
import com.obast.charer.openapi.dto.vo.OpenChargerVo;
import com.obast.charer.openapi.service.IOpenChargerService;
import com.obast.charer.qo.ChargerQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OpenChargerServiceImpl implements IOpenChargerService {

    @Autowired
    private IChargerData chargerData;

    @Override
    public Paging<OpenChargerVo> queryPage(PageRequest<ChargerQueryBo> pageRequest) {
        Paging<Charger> pageList = chargerData.findPage(pageRequest);
        Paging<OpenChargerVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Charger station: pageList.getRows()) {
            newPageList.getRows().add(fillData(station));
        }
        return newPageList;
    }

    @Override
    public OpenChargerVo queryDetail(String stationId) {
        Charger charger = chargerData.findById(stationId);
        return fillData(charger);
    }

    @Override
    public OpenChargerVo queryDetailByDn(String dn) {
        Charger charger = chargerData.findByDn(dn);
        return fillData(charger);
    }

    private OpenChargerVo fillData(Charger charger) {
        return MapstructUtils.convert(charger, OpenChargerVo.class);
    }
}
