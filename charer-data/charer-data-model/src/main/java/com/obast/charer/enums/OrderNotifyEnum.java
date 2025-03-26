package com.obast.charer.enums;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：公告类型枚举
 */

@Getter
public enum OrderNotifyEnum implements IBaseEnum<Integer> {
    hasNotify(1, "已通知", "info", "Y"),
    NoNotify(0, "未通知", "primary", "N"),

    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    OrderNotifyEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<OrderNotifyEnum, Integer> {

        public Converter() {
            super(OrderNotifyEnum.class);
        }
    }

    public static class ExcelConverter implements com.alibaba.excel.converters.Converter<OrderNotifyEnum> {
        @Override
        public Class<OrderNotifyEnum> supportJavaTypeKey() {
            return OrderNotifyEnum.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public WriteCellData<?> convertToExcelData(OrderNotifyEnum enumValue, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            return new WriteCellData<String>(enumValue.getMsg());
        }
    }
}