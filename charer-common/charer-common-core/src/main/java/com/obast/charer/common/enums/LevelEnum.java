package com.obast.charer.common.enums;


import com.obast.charer.common.converter.AbstractEnumConverter;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：启用/停用枚举
 */

@Getter
public enum LevelEnum implements IBaseEnum<Integer> {
    Tenant(1, "运营商"),
    Agent(2, "代理商"),
    Dealer(3, "合作商"),
    ;

    private final int code;
    private final String msg;

    LevelEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<LevelEnum, Integer> {

        public Converter() {
            super(LevelEnum.class);
        }
    }
}