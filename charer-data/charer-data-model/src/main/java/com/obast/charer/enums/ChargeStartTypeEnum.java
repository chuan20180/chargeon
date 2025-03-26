package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩启动类型(1，立即启动，2，定时启动)
 */

@Getter
public enum ChargeStartTypeEnum implements IBaseEnum<String> {
    Now(1, "立即启动", "info", "Y"),
    Timer(2, "定时启动", "info", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    ChargeStartTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<ChargeStartTypeEnum, String> {

        public Converter() {
            super(ChargeStartTypeEnum.class);
        }
    }
}