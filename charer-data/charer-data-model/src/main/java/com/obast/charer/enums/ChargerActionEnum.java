package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电柱协议枚举
 */
@Getter
public enum ChargerActionEnum implements IBaseEnum<String> {
    StartCharge("start", "启动充电", "info", "Y"),
    ;

    private final String code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    ChargerActionEnum(String code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<ChargerActionEnum, String> {

        public Converter() {
            super(ChargerActionEnum.class);
        }
    }
}