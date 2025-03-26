package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：计价规则类型枚举
 */
@Getter
public enum PriceParkPeriodEnum implements IBaseEnum<Integer> {
    Half(1, "半小时", "info", "Y"),
    Hour(2, "小时", "warning", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    PriceParkPeriodEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<PriceParkPeriodEnum, Integer> {

        public Converter() {
            super(PriceParkPeriodEnum.class);
        }
    }
}