package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券抵扣限制枚举
 */

@Getter
public enum CouponApplyEnum implements IBaseEnum<Integer> {
//    Total(0, "适用总额", "success", "Y"),
//    Elec(1, "只适用电费", "danger", "N"),
    Service(2, "只适用服务费", "warning", "N"),
    ServiceAndPark(3, "适用服务费和占位费", "warning", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    CouponApplyEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<CouponApplyEnum, Integer> {

        public Converter() {
            super(CouponApplyEnum.class);
        }
    }
}