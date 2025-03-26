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
public enum OrderTranTypeEnum implements IBaseEnum<Integer> {
    App(1, "app 启动", "danger", "Y"),
    Card(2, "卡启动", "danger", "N"),
    OfflineCard(4, "离线卡启动", "danger", "N"),
    VinCode(5, "vin 码启动充电", "danger", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    OrderTranTypeEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<OrderTranTypeEnum, Integer> {
        public Converter() {
            super(OrderTranTypeEnum.class);
        }
    }

    public static class ExcelConverter implements com.alibaba.excel.converters.Converter<OrderTranTypeEnum> {
        @Override
        public Class<OrderTranTypeEnum> supportJavaTypeKey() {
            return OrderTranTypeEnum.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public WriteCellData<?> convertToExcelData(OrderTranTypeEnum enumValue, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            return new WriteCellData<String>(enumValue.getMsg());
        }
    }
}