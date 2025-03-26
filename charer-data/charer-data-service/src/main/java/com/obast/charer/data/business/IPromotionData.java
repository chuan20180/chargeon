package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;

import com.obast.charer.model.promotion.Promotion;
import com.obast.charer.qo.PromotionQueryBo;

import java.util.List;


public interface IPromotionData extends ICommonData<Promotion, String>, IJPACommonData<Promotion, PromotionQueryBo, String> {
    Promotion findByName(String name);

    List<Promotion> findByStationId(String stationId);

    List<Promotion> findAvailableByStationId(String stationId);

    Promotion findMaxDiscountByStationId(String stationId);

    Promotion findMaxServiceDiscountByStationId(String stationId);

    Promotion findMaxParkDiscountByStationId(String stationId);

    Promotion add(Promotion entity);

    Promotion update(Promotion entity);
}
