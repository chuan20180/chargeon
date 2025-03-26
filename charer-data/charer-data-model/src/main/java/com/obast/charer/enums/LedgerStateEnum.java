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
public enum LedgerStateEnum implements IBaseEnum<Integer> {
    Pending(0, "未结算", "info", "Y"),
    Settled(1, "已结算", "success", "N"),
    Canceled(-2, "已取消", "info", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    LedgerStateEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<LedgerStateEnum, Integer> {

        public Converter() {
            super(LedgerStateEnum.class);
        }
    }
}