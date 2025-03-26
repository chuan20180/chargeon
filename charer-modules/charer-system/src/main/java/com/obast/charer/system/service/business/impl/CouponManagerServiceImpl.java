package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.system.dto.bo.CouponBo;
import com.obast.charer.system.dto.vo.coupon.CouponVo;
import com.obast.charer.converter.I18nToStringConverter;
import com.obast.charer.data.business.ICouponCodeData;
import com.obast.charer.data.business.ICouponData;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.data.business.IStationData;
import com.obast.charer.enums.CouponAcceptEnum;
import com.obast.charer.enums.CouponCodeStateEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.system.listener.event.CouponAddEvent;
import com.obast.charer.system.listener.event.OrderSettledEvent;
import com.obast.charer.system.service.business.ICouponManagerService;
import com.obast.charer.model.coupon.Coupon;
import com.obast.charer.model.coupon.CouponCode;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.station.Station;
import com.obast.charer.qo.CouponQueryBo;
import com.obast.charer.qo.CustomerQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券服务实现
 */
@Service
public class CouponManagerServiceImpl implements ICouponManagerService {

    @Autowired
    private ICouponData couponData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private ICouponCodeData couponCodeData;

    @Override
    public Paging<CouponVo> queryPageList(PageRequest<CouponQueryBo> pageRequest) {
        Paging<Coupon> pageList = couponData.findPage(pageRequest);
        Paging<CouponVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Coupon coupon: pageList.getRows()) {
            newPageList.getRows().add(fillData(coupon));
        }
        return newPageList;
    }

    @Override
    public List<CouponVo> queryList(PageRequest<CouponQueryBo> pageRequest) {
        List<Coupon> list = couponData.findList(pageRequest.getData());
        List<CouponVo> newList = new ArrayList<>();
        for(Coupon coupon: list) {
            newList.add(fillData(coupon));
        }
        return newList;
    }

    @Override
    public CouponVo queryDetail(String stationId) {
        return fillData(couponData.findById(stationId));
    }

    @Override
    @Transactional
    public boolean addCoupon(CouponBo bo) {
        Coupon entity = bo.to(Coupon.class);

        entity.setEnableTime(new Date());

        //名称不可重复
        Coupon repetition = couponData.findByName(entity.getName());
        if (repetition != null) {
            throw new BizException(ErrCode.COUPON_NAME_ALREADY);
        }

        if(bo.getAmount() == null || bo.getAmount().compareTo(new BigDecimal(0)) <= 0) {
            throw new BizException(ErrCode.COUPON_AMOUNT_ERROR);
        }

        if(bo.getExpireTime() == null || bo.getExpireTime().compareTo(new Date()) <= 0) {
            throw new BizException(ErrCode.COUPON_EXPIRE_TIME_ERROR);
        }

        Coupon coupon = couponData.add(entity);

        //发送优惠券添加事件
        SpringUtils.context().publishEvent(new CouponAddEvent(coupon));


        return true;
    }


    @Override
    @Transactional
    public boolean removeCoupon(String couponId) {
        Coupon coupon = getDetail(couponId);
        if (coupon == null) {
            throw new BizException(ErrCode.COUPON_NOT_FOUND);
        }

        List<CouponCode> couponCodes = couponCodeData.findByCouponId(coupon.getId());
        if(!couponCodes.isEmpty()) {
           for(CouponCode couponCode: couponCodes) {
               if(couponCode.getState().equals(CouponCodeStateEnum.Used)) {
                   throw new BizException(ErrCode.COUPON_CODE_EXIST_USED);
               }
               couponCodeData.deleteById(couponCode.getId());
           }
        }

        couponData.deleteById(couponId);
        return true;
    }

    @Override
    @Transactional
    public boolean batchRemoveCoupon(List<String> ids) {
        if(!ids.isEmpty()) {
            for(String id: ids) {
                Coupon coupon = couponData.findById(id);
                if (coupon == null) {
                    throw new BizException(ErrCode.COUPON_NOT_FOUND);
                }
                List<CouponCode> couponCodes = couponCodeData.findByCouponId(coupon.getId());
                if(!couponCodes.isEmpty()) {
                    for(CouponCode couponCode: couponCodes) {
                        if(couponCode.getState().equals(CouponCodeStateEnum.Used)) {
                            throw new BizException(ErrCode.COUPON_CODE_EXIST_USED);
                        }
                        couponCodeData.deleteById(couponCode.getId());
                    }
                }
                couponData.deleteById(id);
            }
        }

        return true;
    }

    @Override
    public Coupon getDetail(String priceId) {
        return couponData.findById(priceId);
    }


    private CouponVo fillData(Coupon coupon) {
        CouponVo vo = MapstructUtils.convert(coupon, CouponVo.class);
        if(vo == null) {
            return null;
        }
        if(coupon.getStationIds() != null) {
            List<String> stationIds = coupon.getStationIds();
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