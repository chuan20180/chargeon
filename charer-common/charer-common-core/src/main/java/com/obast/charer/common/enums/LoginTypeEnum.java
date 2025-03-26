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
public enum LoginTypeEnum implements IBaseEnum<String> {
    Wechat(1, "手机号一键登陆", "default", "Y"),
    Code(2, "手机验证码登陆", "default", "Y"),
    Account(3, "账号登陆", "default", "Y"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    LoginTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<LoginTypeEnum, String> {

        public Converter() {
            super(LoginTypeEnum.class);
        }
    }
}