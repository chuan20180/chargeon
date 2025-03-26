package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：活动类型枚举
 */

@Getter
public enum ChargerDirectiveTypeEnum implements IBaseEnum<Integer> {
    Order(1, "订单", "info", "Y"),

    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    ChargerDirectiveTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<ChargerDirectiveTypeEnum, Integer> {

        public Converter() {
            super(ChargerDirectiveTypeEnum.class);
        }
    }
}