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
public enum OrderDealEnum implements IBaseEnum<Integer> {
    NoDeal(0, "未分成", "info", "Y"),
    Dealed(1, "已分成", "success", "N"),

    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    OrderDealEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<OrderDealEnum, Integer> {

        public Converter() {
            super(OrderDealEnum.class);
        }
    }

    public static class ExcelConverter implements com.alibaba.excel.converters.Converter<OrderDealEnum> {
        @Override
        public Class<OrderDealEnum> supportJavaTypeKey() {
            return OrderDealEnum.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public WriteCellData<?> convertToExcelData(OrderDealEnum enumValue, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            return new WriteCellData<String>(enumValue.getMsg());
        }
    }
}