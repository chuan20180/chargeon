package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IInstantData;
import com.obast.charer.model.Instant;
import com.obast.charer.openapi.dto.bo.InstantBo;
import com.obast.charer.openapi.dto.vo.OpenInstantVo;
import com.obast.charer.openapi.service.IOpenInstantService;
import com.obast.charer.qo.InstantQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OpenInstantServiceImpl implements IOpenInstantService {

    @Autowired
    private IInstantData instantData;

    @Override
    public Paging<OpenInstantVo> queryPage(PageRequest<InstantQueryBo> pageRequest) {
        Paging<Instant> pageList = instantData.findPage(pageRequest);
        Paging<OpenInstantVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Instant recharge: pageList.getRows()) {
            newPageList.getRows().add(fillData(recharge));
        }
        return newPageList;
    }

    @Override
    public OpenInstantVo queryDetail(String stationId) {
        Instant instant = instantData.findById(stationId);
        return fillData(instant);
    }

    @Override
    public OpenInstantVo queryDetailByTranId(String tranId) {
        Instant instant = instantData.findByTranId(tranId);
        return fillData(instant);
    }

    private OpenInstantVo fillData(Instant recharge) {
        return MapstructUtils.convert(recharge, OpenInstantVo.class);
    }

    @Override
    public boolean addInstant(InstantBo bo) {
        Instant entity = bo.to(Instant.class);
        return instantData.add(entity) != null;
    }
}
