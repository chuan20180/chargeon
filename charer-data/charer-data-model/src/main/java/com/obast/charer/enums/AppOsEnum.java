package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券抵扣限制枚举
 */

@Getter
public enum AppOsEnum implements IBaseEnum<String> {
    Wechat(1, "微信小程序用户", "default", "Y"),
    Ios(10, "Ios用户", "default", "Y"),
    Android(20, "Android用户", "default", "Y"),
    Devtools(30, "Devtools用户", "default", "Y"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    AppOsEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<AppOsEnum, String> {

        public Converter() {
            super(AppOsEnum.class);
        }
    }
}