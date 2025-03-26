package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：活动类型枚举
 */

@Getter
public enum ChargerDirectiveStateEnum implements IBaseEnum<Integer> {
    Pending(0, "未执行", "info", "Y"),
    Finished(1, "执行完成", "warning", "N"),
    Fail(-1, "执行失败", "info", "Y"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    ChargerDirectiveStateEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<ChargerDirectiveStateEnum, Integer> {

        public Converter() {
            super(ChargerDirectiveStateEnum.class);
        }
    }
}