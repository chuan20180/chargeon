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
public enum MessageStateEnum implements IBaseEnum<Integer> {
    Read(1, "已读", "success", "Y"),
    UnRead(2, "未读", "danger", "Y"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    MessageStateEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<MessageStateEnum, Integer> {

        public Converter() {
            super(MessageStateEnum.class);
        }
    }
}