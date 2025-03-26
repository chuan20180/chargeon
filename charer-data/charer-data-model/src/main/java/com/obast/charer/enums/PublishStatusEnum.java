package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：发布状态枚举
 */
@Getter
public enum PublishStatusEnum implements IBaseEnum<Integer> {
    Published(1, "已发布", "success", "N"),
    NoPublished(0, "未发布", "danger", "Y"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    PublishStatusEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<PublishStatusEnum, Integer> {

        public Converter() {
            super(PublishStatusEnum.class);
        }
    }
}

