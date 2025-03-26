package com.obast.charer.data.business;

import com.obast.charer.common.model.dto.PriceFee;
import com.obast.charer.common.model.dto.PriceInterval;
import com.obast.charer.qo.PriceQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.price.Price;

import java.util.List;


public interface IPriceData extends ICommonData<Price, String>, IJPACommonData<Price, PriceQueryBo, String> {


    /**
     * 根据设备ID取设备信息
     *
     * @param id 设备ID
     */
    Price findById(String id);

    Price add(Price price);

    Price update(Price price);

    Price findByName(String name);

    PriceFee calcMaxPrice(Price price);

    PriceFee calcMinPrice(Price price);

    PriceFee calcCurrentPrice(Price price);

    List<PriceInterval> calcIntervals(Price price);

}
