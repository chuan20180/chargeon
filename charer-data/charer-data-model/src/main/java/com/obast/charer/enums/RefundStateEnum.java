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
public enum RefundStateEnum implements IBaseEnum<Integer> {
    Pending(0, "待处理", "info", "Y"),
    Processing(2, "处理中", "success", "N"),
    Successful(10, "退款成功", "success", "N"),
    Closed(-1, "退款关闭", "danger", "N"),
    Abnormal(-2, "退款终止", "danger", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;


    RefundStateEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<RefundStateEnum, Integer> {

        public Converter() {
            super(RefundStateEnum.class);
        }
    }
}