package com.obast.charer.enums;

import com.obast.charer.common.enums.IBaseEnum;
import com.obast.charer.common.converter.AbstractEnumConverter;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：启用/停用枚举
 */
@Getter
public enum OnlineStatusEnum implements IBaseEnum<Integer> {
    Online(1, "在线", "success", "N"),
    Offline(0, "离线", "danger", "Y"),
    Unknown(-1, "未知", "info", "Y"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;


    OnlineStatusEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<OnlineStatusEnum, Integer> {

        public Converter() {
            super(OnlineStatusEnum.class);
        }
    }
}