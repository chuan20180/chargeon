package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：启用/停用枚举
 */
@Getter
public enum PmscDirectEnum implements IBaseEnum<String> {
    In(1, "入口", "info", "N"),
    Out(2, "出口", "info", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;


    PmscDirectEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<PmscDirectEnum, String> {

        public Converter() {
            super(PmscDirectEnum.class);
        }
    }
}