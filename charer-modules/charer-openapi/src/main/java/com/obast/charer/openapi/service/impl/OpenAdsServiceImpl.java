package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IAdsData;
import com.obast.charer.model.ads.Ads;
import com.obast.charer.openapi.dto.vo.OpenAdsVo;
import com.obast.charer.openapi.service.IOpenAdsService;
import com.obast.charer.qo.AdsQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAdsServiceImpl implements IOpenAdsService {

    @Autowired
    private IAdsData stationData;

    @Override
    public Paging<OpenAdsVo> queryPage(PageRequest<AdsQueryBo> pageRequest) {
        Paging<Ads> pageList = stationData.findPage(pageRequest);
        Paging<OpenAdsVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Ads station: pageList.getRows()) {
            newPageList.getRows().add(fillData(station));
        }
        return newPageList;
    }

    @Override
    public List<OpenAdsVo> queryList(AdsQueryBo bo) {
        List<Ads> list = stationData.findList(bo);
        List<OpenAdsVo> newList = new ArrayList<>();
        for(Ads ad: list) {
            newList.add(fillData(ad));
        }
        return newList;
    }

    @Override
    public OpenAdsVo queryDetail(String stationId) {
        Ads charger = stationData.findById(stationId);
        return fillData(charger);
    }

    private OpenAdsVo fillData(Ads station) {
        return MapstructUtils.convert(station, OpenAdsVo.class);
    }
}
