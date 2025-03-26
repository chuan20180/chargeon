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
public enum LedgerTypeEnum implements IBaseEnum<String> {
    Platform(1, "平台", "primary", "Y"),
    Tenant(2, "运营商", "success", "N"),
    Agent(3, "代理商", "danger", "N"),
    Dealer(4, "合作商", "info", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    LedgerTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<LedgerTypeEnum, String> {

        public Converter() {
            super(LedgerTypeEnum.class);
        }
    }
}