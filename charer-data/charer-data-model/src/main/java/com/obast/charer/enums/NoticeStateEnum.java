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
public enum NoticeStateEnum implements IBaseEnum<Integer> {
    NoPublish(0, "未发布", "info", "Y"),
    Published(1, "已发布", "success", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;


    NoticeStateEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<NoticeStateEnum, Integer> {

        public Converter() {
            super(NoticeStateEnum.class);
        }
    }
}