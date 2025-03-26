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
public enum TopupSourceEnum implements IBaseEnum<Integer> {
    Online(1, "在线充值", "primary", "N"),
    System(2, "系统赠送", "warning", "N"),


    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    TopupSourceEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<TopupSourceEnum, Integer> {

        public Converter() {
            super(TopupSourceEnum.class);
        }
    }
}