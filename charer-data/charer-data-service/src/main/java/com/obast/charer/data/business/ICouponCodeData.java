package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.coupon.CouponCode;
import com.obast.charer.qo.CouponCodeQueryBo;

import java.util.List;


public interface ICouponCodeData extends ICommonData<CouponCode, String>, IJPACommonData<CouponCode, CouponCodeQueryBo, String> {

    List<CouponCode> findByCustomerId(String customerId);

    List<CouponCode> findAvailableByCustomerId(String customerId);

    CouponCode findMaxDiscountByCustomerId(String customerId);


    CouponCode findMaxDiscountByCustomerIdAndStationId(String customerId, String stationId);

    CouponCode findMaxServiceDiscountByCustomerIdAndStationId(String customerId, String stationId);


    CouponCode findMaxParkDiscountByCustomerIdAndStationId(String customerId, String stationId);


    List<CouponCode> findByCouponId(String couponId);


    CouponCode findByTranId(String tranId);

    CouponCode add(CouponCode couponCode);

}
