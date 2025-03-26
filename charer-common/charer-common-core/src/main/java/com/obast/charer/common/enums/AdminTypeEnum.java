package com.obast.charer.common.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券抵扣限制枚举
 */

@Getter
public enum AdminTypeEnum implements IBaseEnum<String> {
    SuperAdmin(1, "系统管理员", "default", "Y"),
    PlatformUser(2, "平台管理员", "default", "Y"),
    TenantUser(5, "运营商用户", "default", "Y"),
    AgentUser(6, "代理商用户", "default", "Y"),
    DealerUser(7, "合作商用户", "default", "Y"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    AdminTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public String getData() {
        return name();
    }

    public static class Converter extends AbstractEnumConverter<AdminTypeEnum, String> {

        public Converter() {
            super(AdminTypeEnum.class);
        }
    }
}