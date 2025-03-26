package com.obast.charer.system.service.business;

import com.obast.charer.system.dto.bo.CouponBo;
import com.obast.charer.system.dto.vo.coupon.CouponVo;
import com.obast.charer.qo.CouponQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.model.coupon.Coupon;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券服务接口
 */
public interface ICouponManagerService {
    Paging<CouponVo> queryPageList(PageRequest<CouponQueryBo> pageRequest);

    List<CouponVo> queryList(PageRequest<CouponQueryBo> pageRequest);

    CouponVo queryDetail(String stationId);

    boolean addCoupon(CouponBo data);

    boolean removeCoupon(String id);

    boolean batchRemoveCoupon(List<String> ids);

    Coupon getDetail(String id);

}
