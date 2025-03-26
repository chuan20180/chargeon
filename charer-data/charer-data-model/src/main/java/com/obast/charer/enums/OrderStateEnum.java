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
public enum OrderStateEnum implements IBaseEnum<Integer> {
    Pending(0, "未开始", "info", "Y"),
    Processing(1, "进行中", "danger", "N"),
    Finished(2, "已完成", "primary", "N"),
    Settled(10, "已结算", "success", "N"),
    Fail(-1, "已失败", "info", "N"),
    Canceled(-2, "已取消", "info", "N"),
    ;

    private final int code;
    private final String msg;

    private final String listClass;
    private final String isDefault;

    OrderStateEnum(int code, String msg, String listClass, String isDefault) {
        this.code = code;
        this.msg = msg;
        this.listClass = listClass;
        this.isDefault = isDefault;
    }

    @Override
    public Integer getData() {
        return code;
    }

    public static class Converter extends AbstractEnumConverter<OrderStateEnum, Integer> {
        public Converter() {
            super(OrderStateEnum.class);
        }
    }

    public static class ExcelConverter implements com.alibaba.excel.converters.Converter<OrderStateEnum> {
        @Override
        public Class<OrderStateEnum> supportJavaTypeKey() {
            return OrderStateEnum.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public WriteCellData<?> convertToExcelData(OrderStateEnum enumValue, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            return new WriteCellData<String>(enumValue.getMsg());
        }
    }
}