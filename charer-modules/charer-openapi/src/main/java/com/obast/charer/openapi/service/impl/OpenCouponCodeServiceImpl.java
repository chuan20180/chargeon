package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ICouponCodeData;
import com.obast.charer.data.business.IStationData;
import com.obast.charer.enums.CouponScopeEnum;
import com.obast.charer.model.coupon.CouponCode;
import com.obast.charer.model.station.Station;
import com.obast.charer.openapi.dto.vo.OpenCouponCodeVo;
import com.obast.charer.openapi.dto.vo.OpenStationVo;
import com.obast.charer.openapi.service.IOpenCouponCodeService;
import com.obast.charer.qo.CouponCodeQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenCouponCodeServiceImpl implements IOpenCouponCodeService {


    @Autowired
    private ICouponCodeData couponCodeData;

    @Autowired
    private IStationData stationData;

    @Override
    public Paging<OpenCouponCodeVo> queryPage(PageRequest<CouponCodeQueryBo> pageRequest) {
        Paging<CouponCode> pageList  = couponCodeData.findPage(pageRequest);
        Paging<OpenCouponCodeVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(CouponCode orders : pageList.getRows()) {
            newPageList.getRows().add(fillData(orders));
        }
        return newPageList;
    }

    @Override
    public OpenCouponCodeVo queryDetail(String orderId) {
        CouponCode charger  = couponCodeData.findById(orderId);
        return fillData(charger);
    }

    @Override
    public List<OpenCouponCodeVo> queryByCustomerId(String customerId) {
        CouponCodeQueryBo bo = new CouponCodeQueryBo();
        bo.setCustomerId(customerId);

        List<CouponCode> list  = couponCodeData.findList(bo);
        List<OpenCouponCodeVo> newList = new ArrayList<>();
        for(CouponCode couponCode : list) {
            newList.add(fillData(couponCode));
        }
        return newList;
    }

    private OpenCouponCodeVo fillData(CouponCode couponCode) {
        OpenCouponCodeVo vo = MapstructUtils.convert(couponCode, OpenCouponCodeVo.class);
        if(vo == null) {
            return null;
        }

        if(vo.getScope().equals(CouponScopeEnum.Part)) {
            if(!vo.getStationIds().isEmpty()) {
                List<String> stationIds = vo.getStationIds();
                List<Station> stations = stationData.findByIds(stationIds);
                if(stations != null) {
                    List<OpenStationVo> openStationVos = MapstructUtils.convert(stations, OpenStationVo.class);
                    List<String> stationNames = openStationVos.stream().map(OpenStationVo::getName).collect(Collectors.toList());
                    vo.setStationNames(String.join(",",stationNames));
                }
            }
        }
        return vo;
    }
}
