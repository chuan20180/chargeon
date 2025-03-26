package com.obast.charer.common.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券抵扣限制枚举
 */

@Getter
public enum PlatformTypeEnum implements IBaseEnum<String> {
    Weixin("微信小程序用户"),
    App("APP用户"),
    Web("Web用户"),
    ;

    private final String msg;


    PlatformTypeEnum(String msg) {
        this.msg = msg;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<PlatformTypeEnum, String> {

        public Converter() {
            super(PlatformTypeEnum.class);
        }
    }
}