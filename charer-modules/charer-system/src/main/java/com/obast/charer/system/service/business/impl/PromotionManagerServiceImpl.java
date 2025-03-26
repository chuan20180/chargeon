package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.system.dto.bo.PromotionBo;
import com.obast.charer.system.dto.vo.promotion.PromotionVo;
import com.obast.charer.converter.I18nToStringConverter;

import com.obast.charer.data.business.IPromotionData;
import com.obast.charer.data.business.IStationData;

import com.obast.charer.system.service.business.IPromotionManagerService;

import com.obast.charer.model.promotion.Promotion;
import com.obast.charer.model.station.Station;
import com.obast.charer.qo.PromotionQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券服务实现
 */
@Service
public class PromotionManagerServiceImpl implements IPromotionManagerService {

    @Autowired
    private IPromotionData promotionData;

    @Autowired
    private IStationData stationData;


    @Override
    public Paging<PromotionVo> queryPageList(PageRequest<PromotionQueryBo> pageRequest) {
        Paging<Promotion> pageList = promotionData.findPage(pageRequest);
        Paging<PromotionVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Promotion promotion: pageList.getRows()) {
            newPageList.getRows().add(fillData(promotion));
        }
        return newPageList;
    }

    @Override
    public List<PromotionVo> queryList(PageRequest<PromotionQueryBo> pageRequest) {
        List<Promotion> list = promotionData.findList(pageRequest.getData());
        List<PromotionVo> newList = new ArrayList<>();
        for(Promotion promotion: list) {
            newList.add(fillData(promotion));
        }
        return newList;
    }

    @Override
    public PromotionVo queryDetail(String stationId) {
        return fillData(promotionData.findById(stationId));
    }

    @Override
    @Transactional
    public boolean addPromotion(PromotionBo bo) {
        Promotion entity = bo.to(Promotion.class);
        //名称不可重复
        Promotion repetition = promotionData.findByName(entity.getName());
        if (repetition != null) {
            throw new BizException(ErrCode.PROMOTION_NAME_ALREADY);
        }

        if(entity.getStartTime() == null) {
            throw new BizException(ErrCode.PROMOTION_START_TIME_EMPTY);
        }

        if(entity.getEndTime() == null) {
            throw new BizException(ErrCode.PROMOTION_END_TIME_EMPTY);
        }

        if(entity.getStartTime().compareTo(entity.getEndTime()) >= 0) {
            throw new BizException(ErrCode.PROMOTION_RANGE_TIME_ERROR);
        }

        promotionData.add(entity);
        return true;
    }

    @Override
    @Transactional
    public boolean updatePromotion(PromotionBo bo) {
        Promotion entity = bo.to(Promotion.class);
        //名称不可重复
        Promotion repetition = promotionData.findByName(entity.getName());
        if (repetition != null && !repetition.getId().equals(entity.getId())) {
            throw new BizException(ErrCode.PRICE_NAME_ALREADY);
        }
        promotionData.update(entity);
        return true;
    }

    @Override
    public void updateStatus(PromotionBo bo) {
        Promotion promotion = promotionData.findById(bo.getId());
        promotion.setStatus(bo.getStatus());
        promotionData.save(promotion);
    }


    @Override
    @Transactional
    public boolean removePromotion(String promotionId) {
        Promotion promotion = promotionData.findById(promotionId);
        if (promotion == null) {
            throw new BizException(ErrCode.COUPON_NOT_FOUND);
        }

        promotionData.deleteById(promotionId);
        return true;
    }

    @Override
    @Transactional
    public boolean batchRemovePromotion(List<String> ids) {
        if(!ids.isEmpty()) {
            for(String id: ids) {
                Promotion promotion = promotionData.findById(id);
                if (promotion == null) {
                    throw new BizException(ErrCode.COUPON_NOT_FOUND);
                }
                promotionData.deleteById(id);
            }
        }

        return true;
    }

    @Override
    public PromotionVo getDetail(String priceId) {
        return MapstructUtils.convert(promotionData.findById(priceId), PromotionVo.class);
    }


    private PromotionVo fillData(Promotion promotion) {
        PromotionVo vo = MapstructUtils.convert(promotion, PromotionVo.class);
        if(vo == null) {
            return null;
        }
        if(promotion.getStationIds() != null) {
            List<String> stationIds = promotion.getStationIds();
            if(!stationIds.isEmpty()) {
                List<Station> stations = stationData.findByIds(stationIds);
                if(!stations.isEmpty()) {
                    List<String> stationNames = new ArrayList<>();
                    for(Station station: stations) {
                        stationNames.add((new I18nToStringConverter()).i18nToString(station.getName()));
                    }
                    vo.setStationNames(String.join(",", stationNames));
                }
            }
        }

        return vo;
    }
}