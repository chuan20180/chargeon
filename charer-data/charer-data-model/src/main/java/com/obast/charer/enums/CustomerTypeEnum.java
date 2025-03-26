package com.obast.charer.enums;

import com.obast.charer.common.enums.IBaseEnum;
import com.obast.charer.common.converter.AbstractEnumConverter;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券抵扣限制枚举
 */

@Getter
public enum CustomerTypeEnum implements IBaseEnum<String> {
    App(1, "APP用户", "default", "Y"),
    Wechat(2, "Wechat小程序用户", "default", "Y"),

    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    CustomerTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<CustomerTypeEnum, String> {

        public Converter() {
            super(CustomerTypeEnum.class);
        }
    }
}