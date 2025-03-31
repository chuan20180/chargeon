package com.obast.charer.system.dto.vo.orders;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.common.Decimal4Serializer;
import com.obast.charer.common.Decimal5Serializer;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.converter.I18nToStringConverter;
import com.obast.charer.enums.*;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.order.Orders;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OrdersVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Orders.class,convertGenerate = false)
public class OrdersVo extends BaseDto {

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "ID", order = 1)
    @ColumnWidth(32)
    private String id;

    @ApiModelProperty(value = "充电扣款类型")
    private ChargePayTypeEnum chargePayType;

    @ApiModelProperty(value = "充电启动方式")
    private ChargeStartTypeEnum chargeStartType;

    @ApiModelProperty(value = "充电停止方式")
    private ChargeStopTypeEnum chargeStopType;

    @ApiModelProperty(value = "设备名称")
    private String chargerName;

    @ApiModelProperty(value = "设备dn")
    @ExcelProperty(value = "设备dn", order = 2)
    @ColumnWidth(32)
    private String chargerDn;

    @ApiModelProperty(value = "枪号")
    @ExcelProperty(value = "枪号", order = 3)
    @ColumnWidth(8)
    private String gunNo;

    @ApiModelProperty(value = "入场时间")
    private Date inTime;

    @ApiModelProperty(value = "出场时间")
    private Date outTime;

    @ApiModelProperty(value = "客户用户id")
    private String customerId;

    @ApiModelProperty(value = "客户用户名")
    @ExcelProperty(value = "客户用户名", order = 4)
    @ColumnWidth(20)
    private String userName;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "场站名称")
    @ExcelProperty(value = "场站名称", order = 5,  converter = I18nToStringConverter.class)
    private I18nField stationName;

    @ApiModelProperty(value = "场站地址")
//    @ExcelProperty(value = "场站地址", converter = I18nToStringConverter.class)
    private I18nField stationAddress;

    @ApiModelProperty(value = "订单号")
    @ExcelProperty(value = "订单号", order = 6)
    @ColumnWidth(26)
    private String tranId;

    @ApiModelProperty(value = "交易类型")
//    @ExcelProperty(value = "交易类型", converter = OrderTranTypeEnum.ExcelConverter.class)
    @Convert(converter = OrderTranTypeEnum.Converter.class)
    private OrderTranTypeEnum tranType;

    @ApiModelProperty(value = "交易时间")
//    @ExcelProperty(value = "交易时间")
    private Date tranTime;

    @ApiModelProperty(value = "开始时间")
    @ExcelProperty(value = "开始时间", order = 7)
    @ColumnWidth(25)
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @ExcelProperty(value = "结束时间", order = 8)
    @ColumnWidth(25)
    private Date endTime;

    @ApiModelProperty(value = "尖单价")
    @ExcelProperty(value = "尖单价", order = 9)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal5Serializer.class)
    private Float sharpPrice;

    @ApiModelProperty(value = "尖电量")
    @ExcelProperty(value = "尖电量" , order = 10)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float sharpQty;

    @ApiModelProperty(value = "计损尖电量")
    @ExcelProperty(value = "计损尖电量" , order = 11)
    @ColumnWidth(15)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float sharpQuantity;

    @ApiModelProperty(value = "尖金额")
    @ExcelProperty(value = "尖金额" , order = 12)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float sharpAmount;

    @ApiModelProperty(value = "峰单价")
    @ExcelProperty(value = "峰单价", order = 13)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal5Serializer.class)
    private Float peakPrice;

    @ApiModelProperty(value = "峰电量")
    @ExcelProperty(value = "峰电量", order = 14)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float peakQty;

    @ApiModelProperty(value = "计损峰电量")
    @ExcelProperty(value = "计损峰电量", order = 15)
    @ColumnWidth(15)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float peakQuantity;

    @ApiModelProperty(value = "峰金额")
    @ExcelProperty(value = "峰金额", order = 16)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float peakAmount;

    @ApiModelProperty(value = "平单价")
    @ExcelProperty(value = "平单价", order = 17)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal5Serializer.class)
    private Float flatPrice;

    @ApiModelProperty(value = "平电量")
    @ExcelProperty(value = "平电量", order = 18)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float flatQty;

    @ApiModelProperty(value = "计损平电量")
    @ExcelProperty(value = "计损平电量", order = 19)
    @ColumnWidth(15)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float flatQuantity;

    @ApiModelProperty(value = "平金额")
    @ExcelProperty(value = "平金额", order = 20)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float flatAmount;

    @ApiModelProperty(value = "谷单价")
    @ExcelProperty(value = "谷单价", order = 21)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal5Serializer.class)
    private Float valleyPrice;

    @ApiModelProperty(value = "谷电量")
    @ExcelProperty(value = "谷电量", order = 22)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float valleyQty;

    @ApiModelProperty(value = "计损谷电量")
    @ExcelProperty(value = "计损谷电量", order = 23)
    @ColumnWidth(15)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float valleyQuantity;

    @ApiModelProperty(value = "谷金额")
    @ExcelProperty(value = "谷金额", order = 24)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float valleyAmount;

    @ApiModelProperty(value = "电表总起值")
    @ExcelProperty(value = "电表总起值", order = 25)
    @ColumnWidth(15)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float quantityStart;

    @ApiModelProperty(value = "电表总止值")
    @ExcelProperty(value = "电表总止值", order = 26)
    @ColumnWidth(15)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float quantityEnd;

    @ApiModelProperty(value = "总电量")
    @ExcelProperty(value = "总电量", order = 27)
    @ColumnWidth(10)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float totalQty;

    @ApiModelProperty(value = "计损总电量")
    @ExcelProperty(value = "计损总电量", order = 28)
    @ColumnWidth(15)
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float totalQuantity;

    @ApiModelProperty(value = "消费金额")
    @ExcelProperty(value = "消费金额", order = 29)
    @ColumnWidth(12)
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "电费金额")
    @ExcelProperty(value = "电费金额", order = 29)
    @ColumnWidth(12)
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal elecAmount;

    @ApiModelProperty(value = "服务费金额")
    @ExcelProperty(value = "服务费金额", order = 29)
    @ColumnWidth(12)
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal serviceAmount;

    @ApiModelProperty(value = "订单金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    @ColumnWidth(12)
    @ExcelProperty(value = "订单金额", order = 30)
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "结算金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    @ColumnWidth(12)
    @ExcelProperty(value = "结算金额", order = 30)
    private BigDecimal settledAmount;

    @ApiModelProperty(value = "优惠金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    @ColumnWidth(12)
    @ExcelProperty(value = "优惠金额", order = 32)
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "欠款金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    @ColumnWidth(12)
    @ExcelProperty(value = "欠款金额", order = 33)
    private BigDecimal debitAmount;


    @ApiModelProperty(value = "占位金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    @ColumnWidth(12)
    @ExcelProperty(value = "占位金额", order = 33)
    private BigDecimal parkAmount;


    @ApiModelProperty(value = "SOC")
    private Short soc;

    @ApiModelProperty(value = "充电分钟数")
    @ExcelProperty(value = "充电分钟数",order = 36)
    @ColumnWidth(15)
    private Short chargeMinute;

    @ApiModelProperty(value = "剩余分钟数")
    private Short remainMinute;

    @ApiModelProperty(value = "实时电压")
    private Float voltage;

    @ApiModelProperty(value = "实时电流")
    private Float current;

    @ApiModelProperty(value = "实时功率")
    private Float power;

    @ApiModelProperty(value = "电动汽车唯一标识")
    private String vin;

    @ApiModelProperty(value = "停止原因")
    @ExcelProperty(value = "停止原因", order = 37)
    @ColumnWidth(15)
    private String stopReason;

    @ApiModelProperty(value = "物理卡号")
    private String physicalCardNo;

    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态",order = 38, converter = OrderStateEnum.ExcelConverter.class)
    @ColumnWidth(15)
    private OrderStateEnum state;

    @ApiModelProperty(value = "分成")
    @ExcelProperty(value = "分成",order = 39, converter = OrderDealEnum.ExcelConverter.class)
    @ColumnWidth(15)
    private OrderDealEnum deal;

    @ApiModelProperty(value = "描述")
    private String note;

}
