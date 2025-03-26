package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.model.dto.PriceFee;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IPriceData;
import com.obast.charer.model.price.Price;
import com.obast.charer.openapi.dto.vo.OpenPriceVo;
import com.obast.charer.openapi.service.IOpenPriceService;
import com.obast.charer.qo.PriceQueryBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpenPriceServiceImpl implements IOpenPriceService {


    @Autowired
    private IPriceData priceData;

    @Override
    public Paging<OpenPriceVo> queryPage(PageRequest<PriceQueryBo> pageRequest) {
        Paging<Price> pageList = priceData.findPage(pageRequest);
        return MapstructUtils.convert(pageList, OpenPriceVo.class);
    }

    @Override
    public OpenPriceVo queryDetail(String id) {
        Price price = priceData.findById(id);
        return fillData(price);
    }

    private OpenPriceVo fillData(Price price) {
        OpenPriceVo openPriceVo = MapstructUtils.convert(price, OpenPriceVo.class);
        PriceFee currentFee = priceData.calcCurrentPrice(price);
        openPriceVo.setCurrentPrice(currentFee);
        openPriceVo.setIntervals(priceData.calcIntervals(price));
        return openPriceVo;
    }

}
