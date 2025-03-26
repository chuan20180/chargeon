package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IAdsData;
import com.obast.charer.data.business.IPromotionData;
import com.obast.charer.model.ads.Ads;
import com.obast.charer.model.promotion.Promotion;
import com.obast.charer.openapi.dto.vo.OpenAdsVo;
import com.obast.charer.openapi.dto.vo.OpenPromotionVo;
import com.obast.charer.openapi.service.IOpenAdsService;
import com.obast.charer.openapi.service.IOpenPromotionService;
import com.obast.charer.qo.AdsQueryBo;
import com.obast.charer.qo.PromotionQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenPromotionServiceImpl implements IOpenPromotionService {

    @Autowired
    private IPromotionData promotionData;

    @Override
    public Paging<OpenPromotionVo> queryPage(PageRequest<PromotionQueryBo> pageRequest) {
        Paging<Promotion> pageList = promotionData.findPage(pageRequest);
        Paging<OpenPromotionVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Promotion promotion: pageList.getRows()) {
            newPageList.getRows().add(fillData(promotion));
        }
        return newPageList;
    }

    @Override
    public List<OpenPromotionVo> queryList(PromotionQueryBo bo) {
        List<Promotion> list = promotionData.findList(bo);
        List<OpenPromotionVo> newList = new ArrayList<>();
        for(Promotion promotion: list) {
            newList.add(fillData(promotion));
        }
        return newList;
    }

    @Override
    public List<OpenPromotionVo> queryAvailableListById(String id) {
        List<Promotion> list = promotionData.findAvailableByStationId(id);
        List<OpenPromotionVo> newList = new ArrayList<>();
        for(Promotion promotion: list) {
            newList.add(fillData(promotion));
        }
        return newList;
    }

    @Override
    public OpenPromotionVo queryDetail(String id) {
        Promotion promotion = promotionData.findById(id);
        return fillData(promotion);
    }

    private OpenPromotionVo fillData(Promotion promotion) {
        return MapstructUtils.convert(promotion, OpenPromotionVo.class);
    }
}
