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
public enum DiscountTypeEnum implements IBaseEnum<Integer> {
    Coupon(1, "优惠券抵扣", "warning", "N"),
    Quota(2, "服务费打折抵扣", "warning", "N"),
    Give(3, "赠送金额抵扣", "warning", "N"),
    Promotion(4, "活动服务费打折抵扣", "warning", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    DiscountTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<DiscountTypeEnum, Integer> {

        public Converter() {
            super(DiscountTypeEnum.class);
        }
    }
}