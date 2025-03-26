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
public enum EnableStatusEnum implements IBaseEnum<Integer> {
    Enabled(1, "启用", "success", "N"),
    Disabled(0, "停用", "danger", "Y"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;


    EnableStatusEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<EnableStatusEnum, Integer> {

        public Converter() {
            super(EnableStatusEnum.class);
        }
    }
}