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
public enum ChargerGunStateEnum implements IBaseEnum<Integer> {
    Unknow(-1, "未知", "info", "N"),
    Offline(0, "离线", "info", "N"),
    Fail(1, "故障", "warning", "N"),
    Idle(2, "空闲", "success", "Y"),
    Charging(3, "充电中", "danger", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    ChargerGunStateEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<ChargerGunStateEnum, Integer> {

        public Converter() {
            super(ChargerGunStateEnum.class);
        }
    }
}