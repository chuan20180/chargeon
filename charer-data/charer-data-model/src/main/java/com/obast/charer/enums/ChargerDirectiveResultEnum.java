package com.obast.charer.enums;

import com.obast.charer.common.enums.IBaseEnum;
import com.obast.charer.common.converter.AbstractEnumConverter;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：活动类型枚举
 */

@Getter
public enum ChargerDirectiveResultEnum implements IBaseEnum<Integer> {
    Pending(0, "未执行", "info", "N"),
    Successful(1, "成功", "success", "Y"),
    Fail(-1, "失败", "danger", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    ChargerDirectiveResultEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<ChargerDirectiveResultEnum, Integer> {

        public Converter() {
            super(ChargerDirectiveResultEnum.class);
        }
    }
}