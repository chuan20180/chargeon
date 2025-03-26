package com.obast.charer.enums;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：公告类型枚举
 */

@Getter
public enum ParkSettleEnum implements IBaseEnum<Integer> {
    NoSettled(0, "未结算", "info", "Y"),
    Settled(10, "已结算", "success", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    ParkSettleEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<ParkSettleEnum, Integer> {
        public Converter() {
            super(ParkSettleEnum.class);
        }
    }

    public static class ExcelConverter implements com.alibaba.excel.converters.Converter<ParkSettleEnum> {
        @Override
        public Class<ParkSettleEnum> supportJavaTypeKey() {
            return ParkSettleEnum.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public WriteCellData<?> convertToExcelData(ParkSettleEnum enumValue, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            return new WriteCellData<String>(enumValue.getMsg());
        }
    }
}