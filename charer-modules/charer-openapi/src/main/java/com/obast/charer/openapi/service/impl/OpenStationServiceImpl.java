package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.data.business.IChargerGunData;
import com.obast.charer.data.business.IPriceData;
import com.obast.charer.data.business.IStationData;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.enums.ChargerGunSpeedEnum;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.price.Price;
import com.obast.charer.model.product.Product;
import com.obast.charer.model.station.Station;
import com.obast.charer.openapi.dto.vo.OpenStationVo;
import com.obast.charer.openapi.service.IOpenStationService;
import com.obast.charer.qo.StationQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenStationServiceImpl implements IOpenStationService {

    @Autowired
    private IProductData productData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private IPriceData priceData;

    @Autowired
    private IChargerGunData chargerGunData;

    @Override
    public Paging<OpenStationVo> queryPage(PageRequest<StationQueryBo> pageRequest) {
        Paging<Station> pageList = stationData.findPage(pageRequest);
        Paging<OpenStationVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Station station: pageList.getRows()) {
            newPageList.getRows().add(fillData(station));
        }
        return newPageList;
    }

    @Override
    public List<OpenStationVo> queryList(StationQueryBo queryBo) {
        List<Station> list = stationData.findList(queryBo);
        List<OpenStationVo> newList = new ArrayList<>();
        for(Station station: list) {
            newList.add(fillData(station));
        }
        return newList;
    }

    @Override
    public OpenStationVo queryDetail(String stationId) {
        Station charger = stationData.findById(stationId);
        return fillData(charger);
    }

    private OpenStationVo fillData(Station station) {
        OpenStationVo vo = MapstructUtils.convert(station, OpenStationVo.class);
        if(vo == null) {
            return null;
        }
        int availableSlowGunQty = 0;
        int unavailableSlowGunQty = 0;
        int totalSlowGunQty = 0;

        int availableQuickGunQty = 0;
        int unavailableQuickGunQty = 0;
        int totalQuickGunQty = 0;

        List<Charger> chargers = chargerData.findAvailableByStationId(station.getId());
        vo.setChargers(chargers);

        for(Charger charger: chargers) {
            Price price = priceData.findById(charger.getPriceId());
            if(price == null) {
                throw new BizException(ErrCode.PRICE_NOT_FOUND);
            }
            price.setCurrentPrice(priceData.calcCurrentPrice(price));
            price.setMaxPrice(priceData.calcMaxPrice(price));
            price.setMinPrice(priceData.calcMinPrice(price));
            charger.setPrice(price);

            Product product = productData.findByProductKey(charger.getProductKey());
            if(product == null) {
                throw new BizException(ErrCode.PRODUCT_NOT_FOUND);
            }

            Product.Charger productCharger = product.getCharger();
            if(productCharger == null) {
                throw new BizException(ErrCode.PRODUCT_PROPERTIES_NOT_FOUND);
            }

            List<ChargerGun> chargerGuns = chargerGunData.findByChargerId(charger.getId());
            charger.setGuns(chargerGuns);

            for(ChargerGun chargerGun: chargerGuns) {
                if(chargerGun.getState().equals(ChargerGunStateEnum.Idle)) {
                    if(productCharger.getSpeed().equals(ChargerGunSpeedEnum.Quick)) {
                        availableQuickGunQty++;
                    }
                    if(productCharger.getSpeed().equals(ChargerGunSpeedEnum.Slow)) {
                        availableSlowGunQty++;
                    }
                }  else {
                    if(productCharger.getSpeed().equals(ChargerGunSpeedEnum.Quick)) {
                        unavailableQuickGunQty++;
                    }
                    if(productCharger.getSpeed().equals(ChargerGunSpeedEnum.Slow)) {
                        unavailableSlowGunQty++;
                    }
                }

                if(productCharger.getSpeed().equals(ChargerGunSpeedEnum.Quick)) {
                    totalQuickGunQty++;
                }
                if(productCharger.getSpeed().equals(ChargerGunSpeedEnum.Slow)) {
                    totalSlowGunQty++;
                }
            }
        }

        BigDecimal minPrice = new BigDecimal(0);
        BigDecimal maxPrice = new BigDecimal(0);

        List<Price> prices = new ArrayList<>();

        for(Charger charger: chargers) {
            Price price = charger.getPrice();

            if(!prices.contains(price)) {
                prices.add(price);
            }

            if(minPrice.compareTo(new BigDecimal(0)) == 0) {
                minPrice = price.getMinPrice().getAmount();
            }

            if(maxPrice.compareTo(new BigDecimal(0)) == 0) {
                maxPrice = price.getMaxPrice().getAmount();
            }

            if(price.getMinPrice().getAmount().compareTo(minPrice) < 0) {
                minPrice = price.getMinPrice().getAmount();
            }

            if(price.getMaxPrice().getAmount().compareTo(maxPrice) > 0) {
                maxPrice = price.getMaxPrice().getAmount();
            }
        }

        vo.setPrices(prices);
        vo.setMinPrice(minPrice);
        vo.setMaxPrice(maxPrice);
        vo.setAvailableGunQty(availableQuickGunQty + availableSlowGunQty);
        vo.setAvailableSlowGunQty(availableSlowGunQty);
        vo.setUnavailableSlowGunQty(unavailableSlowGunQty);
        vo.setTotalSlowGunQty(totalSlowGunQty);

        vo.setAvailableQuickGunQty(availableQuickGunQty);
        vo.setUnavailableQuickGunQty(unavailableQuickGunQty);
        vo.setTotalQuickGunQty(totalQuickGunQty);

        return vo;
    }
}
