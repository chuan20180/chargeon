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
public enum ChargerGunCurrentEnum implements IBaseEnum<Integer> {
    DC(1, "直流", "info", "N"),
    AC(2, "交流", "info", "N"),

    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    ChargerGunCurrentEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<ChargerGunCurrentEnum, Integer> {

        public Converter() {
            super(ChargerGunCurrentEnum.class);
        }
    }
}