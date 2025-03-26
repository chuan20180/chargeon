package com.obast.charer.common.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：计价规则类型枚举
 */
@Getter
public enum PriceTypeEnum implements IBaseEnum<Integer> {
    Standard(1, "标准计价", "info", "Y"),
    Tou(2, "分时计价", "warning", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    PriceTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<PriceTypeEnum, Integer> {

        public Converter() {
            super(PriceTypeEnum.class);
        }
    }
}