package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值方案类型枚举
 */
@Getter
public enum RechargeTypeEnum implements IBaseEnum<Integer> {
    Normal(1, "标准", "info", "Y"),
    Give(2, "满赠", "success", "N"),
    Minus(3, "满减", "danger", "N"),
    Quota(4, "服务费打折", "warning", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    RechargeTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<RechargeTypeEnum, Integer> {

        public Converter() {
            super(RechargeTypeEnum.class);
        }
    }
}