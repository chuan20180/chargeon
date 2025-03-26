package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：订单结算类型
 */

@Getter
public enum OrderSettleTypeEnum implements IBaseEnum<Integer> {
    Elec(1, "电费", "primary", "N"),
    Service(2, "服务费", "warning", "N"),
    Park(3, "占位费", "warning", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    OrderSettleTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<OrderSettleTypeEnum, Integer> {

        public Converter() {
            super(OrderSettleTypeEnum.class);
        }
    }
}