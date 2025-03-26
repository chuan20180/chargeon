package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.system.dto.bo.CouponCodeBo;
import com.obast.charer.system.dto.vo.coupon.CouponCodeVo;
import com.obast.charer.converter.I18nToStringConverter;
import com.obast.charer.data.business.ICouponCodeData;
import com.obast.charer.data.business.IStationData;
import com.obast.charer.enums.CouponCodeStateEnum;
import com.obast.charer.system.service.business.ICouponCodeManagerService;
import com.obast.charer.model.coupon.CouponCode;
import com.obast.charer.model.station.Station;
import com.obast.charer.qo.CouponCodeQueryBo;
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
public class CouponCodeManagerServiceImpl implements ICouponCodeManagerService {

    @Autowired
    private ICouponCodeData couponCodeData;

    @Autowired
    private IStationData stationData;

    @Override
    public Paging<CouponCodeVo> queryPageList(PageRequest<CouponCodeQueryBo> pageRequest) {
        Paging<CouponCode> pageList = couponCodeData.findPage(pageRequest);
        Paging<CouponCodeVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(CouponCode couponCode: pageList.getRows()) {
            newPageList.getRows().add(fillData(couponCode));
        }
        return newPageList;
    }

    @Override
    public List<CouponCodeVo> queryList(PageRequest<CouponCodeQueryBo> pageRequest) {
        List<CouponCode> list = couponCodeData.findList(pageRequest.getData());
        List<CouponCodeVo> newList = new ArrayList<>();
        for(CouponCode couponCode: list) {
            newList.add(fillData(couponCode));
        }
        return newList;
    }

    @Override
    public CouponCodeVo queryDetail(String id) {
        return fillData(couponCodeData.findById(id));
    }

    @Override
    public boolean addCouponCode(CouponCodeBo bo) {
        CouponCode di = bo.to(CouponCode.class);
        //编号不可重复
        CouponCode repetition = couponCodeData.findByTranId(di.getTranId());
        if (repetition != null) {
            throw new BizException(ErrCode.COUPON_TRAN_ID_ALREADY);
        }
        return couponCodeData.save(di) != null;
    }

    @Override
    @Transactional
    public boolean removeCouponCode(String id) {
        CouponCode couponCode = getDetail(id);
        if (couponCode == null) {
            throw new BizException(ErrCode.COUPON_CODE_NOT_EXIST);
        }

        if (!couponCode.getState().equals(CouponCodeStateEnum.UnUsed)) {
            throw new BizException(ErrCode.COUPON_CODE_ALREADY_USED);
        }

        couponCodeData.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean batchRemoveCouponCode(List<String> ids) {
        for(String id: ids) {
            CouponCode couponCode = getDetail(id);
            if (couponCode == null) {
                throw new BizException(ErrCode.COUPON_CODE_NOT_EXIST);
            }

            if (!couponCode.getState().equals(CouponCodeStateEnum.UnUsed)) {
                throw new BizException(ErrCode.COUPON_CODE_ALREADY_USED);
            }
            couponCodeData.deleteById(id);
        }
        return true;
    }

    @Override
    public CouponCode getDetail(String priceId) {
        return couponCodeData.findById(priceId);
    }

    private CouponCodeVo fillData(CouponCode couponCode) {
        CouponCodeVo vo = MapstructUtils.convert(couponCode, CouponCodeVo.class);
        if(vo == null) {
            return null;
        }
        if(couponCode.getStationIds() != null) {
            List<String> stationIds = couponCode.getStationIds();
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
