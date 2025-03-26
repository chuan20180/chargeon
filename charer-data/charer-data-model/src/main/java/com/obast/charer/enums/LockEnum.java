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
public enum LockEnum implements IBaseEnum<Integer> {
    Locked(1, "已锁定", "default", "Y"),
    UnLocked(0, "未锁定", "default", "Y"),

    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    LockEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<LockEnum, Integer> {

        public Converter() {
            super(LockEnum.class);
        }
    }
}