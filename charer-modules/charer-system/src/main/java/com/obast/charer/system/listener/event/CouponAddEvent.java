package com.obast.charer.system.listener.event;

import com.obast.charer.model.coupon.Coupon;
import com.obast.charer.model.order.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 结算完成事件
 **/

@Data
@AllArgsConstructor
public class CouponAddEvent implements Serializable {

    private Coupon coupon;

}
