package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券抵扣限制枚举
 */

@Getter
public enum PageTypeEnum implements IBaseEnum<String> {
    Notification(1, "通知", "default", "Y"),
    Notice(2, "公告", "default", "Y"),
    Topup(3, "充值", "default", "Y"),
    Order(4, "订单", "default", "Y"),
    Coupon(5, "优惠券", "default", "Y"),
    ;

    private final int code;

    private final String msg;

    private final String listClass;
    private final String isDefault;

    PageTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<PageTypeEnum, String> {

        public Converter() {
            super(PageTypeEnum.class);
        }
    }
}