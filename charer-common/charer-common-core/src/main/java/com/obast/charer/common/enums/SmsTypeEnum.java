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
public enum SmsTypeEnum implements IBaseEnum<Integer> {
    Login(1, "登陆"),
    Register(2, "注册"),
    Notify(3, "短信通知"),
    ;

    private final int code;
    private final String msg;

    SmsTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<SmsTypeEnum, Integer> {

        public Converter() {
            super(SmsTypeEnum.class);
        }
    }
}