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
public enum SysConfigDataTypeEnum implements IBaseEnum<Integer> {
    Text(1, "文本", "info", "Y"),
    Textarea(2,"多行文本", "info", "Y"),
    Int(3, "整形", "success", "N"),
    Float(4, "浮点", "danger", "N"),
    Decimal(5, "货币", "danger", "N"),
    I18nText(6, "多语言文本", "warning", "N"),
    I18nTextarea(7, "多语言多行文本", "warning", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    SysConfigDataTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<SysConfigDataTypeEnum, Integer> {

        public Converter() {
            super(SysConfigDataTypeEnum.class);
        }
    }
}