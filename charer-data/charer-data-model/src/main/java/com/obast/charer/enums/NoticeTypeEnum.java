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
public enum NoticeTypeEnum implements IBaseEnum<String> {
    Notice(1, "通知", "info", "Y"),
    Announcement(2, "公告", "success", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;


    NoticeTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<NoticeTypeEnum, String> {

        public Converter() {
            super(NoticeTypeEnum.class);
        }
    }
}