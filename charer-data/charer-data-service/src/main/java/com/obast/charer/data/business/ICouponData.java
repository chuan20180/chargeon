package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.coupon.Coupon;
import com.obast.charer.qo.CouponQueryBo;


public interface ICouponData extends ICommonData<Coupon, String>, IJPACommonData<Coupon, CouponQueryBo, String> {
    Coupon findByName(String name);

    Coupon add(Coupon coupon);
}
