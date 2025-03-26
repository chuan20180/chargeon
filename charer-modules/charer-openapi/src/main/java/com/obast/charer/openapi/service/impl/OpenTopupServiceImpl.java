package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ITopupData;
import com.obast.charer.model.topup.Topup;
import com.obast.charer.openapi.dto.bo.TopupBo;
import com.obast.charer.openapi.dto.vo.OpenTopupVo;
import com.obast.charer.openapi.service.IOpenTopupService;
import com.obast.charer.qo.TopupQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OpenTopupServiceImpl implements IOpenTopupService {

    @Autowired
    private ITopupData topupData;

    @Override
    public Paging<OpenTopupVo> queryPage(PageRequest<TopupQueryBo> pageRequest) {
        Paging<Topup> pageList = topupData.findPage(pageRequest);
        Paging<OpenTopupVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Topup recharge: pageList.getRows()) {
            newPageList.getRows().add(fillData(recharge));
        }
        return newPageList;
    }

    @Override
    public OpenTopupVo queryDetail(String stationId) {
        Topup topup = topupData.findById(stationId);
        return fillData(topup);
    }

    @Override
    public OpenTopupVo queryDetailByTranId(String tranId) {
        Topup topup = topupData.findByTranId(tranId);
        return fillData(topup);
    }

    private OpenTopupVo fillData(Topup recharge) {
        return MapstructUtils.convert(recharge, OpenTopupVo.class);
    }

    @Override
    public boolean addTopup(TopupBo bo) {
        Topup entity = bo.to(Topup.class);
        return topupData.add(entity) != null;
    }
}
