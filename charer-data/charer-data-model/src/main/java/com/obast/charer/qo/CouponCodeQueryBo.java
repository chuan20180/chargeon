package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.CouponCodeStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券查询视图
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponCodeQueryBo extends BaseDto {

    private String id;

    private String couponId;

    private String tranId;

    private CouponCodeStateEnum state;

    private String customerId;

}
