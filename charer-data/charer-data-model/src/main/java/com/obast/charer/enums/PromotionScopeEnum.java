package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券适用范围枚举
 */

@Getter
public enum PromotionScopeEnum implements IBaseEnum<Integer> {
    All(0, "所有场站", "success", "Y"),
    Part(1, "部分场站", "danger", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    PromotionScopeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<PromotionScopeEnum, Integer> {

        public Converter() {
            super(PromotionScopeEnum.class);
        }
    }
}