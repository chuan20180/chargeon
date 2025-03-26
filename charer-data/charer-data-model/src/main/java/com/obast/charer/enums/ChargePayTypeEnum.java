package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：公告类型枚举
 */

@Getter
public enum ChargePayTypeEnum implements IBaseEnum<String> {
    Balance(1, "余额充电", "primary", "Y"),
    Instant(2, "即充即退", "success", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    ChargePayTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<ChargePayTypeEnum, String> {

        public Converter() {
            super(ChargePayTypeEnum.class);
        }
    }
}