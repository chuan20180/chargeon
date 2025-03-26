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
public enum ChannelIdentifierEnum implements IBaseEnum<String> {
    Sms(1, "短信", "success", "N"),
    App(2, "App通知", "success", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;


    ChannelIdentifierEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<ChannelIdentifierEnum, String> {

        public Converter() {
            super(ChannelIdentifierEnum.class);
        }
    }
}