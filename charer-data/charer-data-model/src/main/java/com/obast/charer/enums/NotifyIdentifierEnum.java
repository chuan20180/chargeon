package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：通知类型枚举
 */
@Getter
public enum NotifyIdentifierEnum implements IBaseEnum<String> {
    ChargeStart(1, "充电开始通知", "success", "N"),
    ChargeFinish(2, "充电完成通知", "success", "N"),
    ChargeStop(3, "充电停止通知", "success", "N"),

    TopupSuccess(4, "充值成功通知", "success", "N"),

    BalanceNotEnouth(10, "余额不足通知", "success", "N"),

    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;


    NotifyIdentifierEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<NotifyIdentifierEnum, String> {

        public Converter() {
            super(NotifyIdentifierEnum.class);
        }
    }
}