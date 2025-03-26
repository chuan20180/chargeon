package com.obast.charer.enums;

import com.obast.charer.common.enums.IBaseEnum;
import com.obast.charer.common.converter.AbstractEnumConverter;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：活动类型枚举
 */

@Getter
public enum ChargerGunSpeedEnum implements IBaseEnum<Integer> {
    Slow(1, "慢充", "info", "N"),
    Quick(2, "快充", "info", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    ChargerGunSpeedEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<ChargerGunSpeedEnum, Integer> {

        public Converter() {
            super(ChargerGunSpeedEnum.class);
        }
    }
}