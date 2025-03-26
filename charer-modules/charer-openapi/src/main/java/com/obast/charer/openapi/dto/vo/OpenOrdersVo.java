package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.common.Decimal4Serializer;
import com.obast.charer.common.Decimal5Serializer;
import com.obast.charer.enums.OrderStateEnum;
import com.obast.charer.enums.OrderTranTypeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.order.Orders;
import com.obast.charer.converter.I18nToStringConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenOrdersVo")
@Data
@AutoMapper(target = Orders.class,uses = I18nToStringConverter.class, convertGenerate = false)
public class OpenOrdersVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "设备名称")
    private String chargerName;

    @ApiModelProperty(value = "设备dn")
    private String chargerDn;

    @ApiModelProperty(value = "客户用户id")
    private String customerId;

    @ApiModelProperty(value = "客户用户名")
    private String userName;

    @ApiModelProperty(value = "枪id")
    private String gunNo;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "场站名称")
    private String stationName;

    @ApiModelProperty(value = "场站地址")
    private String stationAddress;

    @ApiModelProperty(value = "订单号")
    private String tranId;

    @ApiModelProperty(value = "交易类型")
    private OrderTranTypeEnum tranType;

    @ApiModelProperty(value = "交易时间")
    private Date tranTime;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "尖单价")
    @JsonSerialize(using = Decimal5Serializer.class)
    private Float sharpPrice;

    @ApiModelProperty(value = "尖电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float sharpQty;

    @ApiModelProperty(value = "计损尖电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float sharpQuantity;

    @ApiModelProperty(value = "尖金额")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float sharpAmount;

    @ApiModelProperty(value = "峰单价")
    @JsonSerialize(using = Decimal5Serializer.class)
    private Float peakPrice;

    @ApiModelProperty(value = "峰电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float peakQty;

    @ApiModelProperty(value = "计损峰电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float peakQuantity;

    @ApiModelProperty(value = "峰金额")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float peakAmount;

    @ApiModelProperty(value = "平单价")
    @JsonSerialize(using = Decimal5Serializer.class)
    private Float flatPrice;

    @ApiModelProperty(value = "平电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float flatQty;

    @ApiModelProperty(value = "计损平电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float flatQuantity;

    @ApiModelProperty(value = "平金额")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float flatAmount;

    @ApiModelProperty(value = "谷单价")
    @JsonSerialize(using = Decimal5Serializer.class)
    private Float valleyPrice;

    @ApiModelProperty(value = "谷电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float valleyQty;

    @ApiModelProperty(value = "计损谷电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float valleyQuantity;

    @ApiModelProperty(value = "谷金额")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float valleyAmount;

    @ApiModelProperty(value = "电表总起值")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float quantityStart;

    @ApiModelProperty(value = "电表总止值")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float quantityEnd;

    @ApiModelProperty(value = "总电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float totalQty;

    @ApiModelProperty(value = "计损总电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private Float totalQuantity;

    @ApiModelProperty(value = "消费金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "订单总额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "电费金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal elecAmount;

    @ApiModelProperty(value = "服务费金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal serviceAmount;

    @ApiModelProperty(value = "占位费金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal parkAmount;

    @ApiModelProperty(value = "结算金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal settledAmount;


    @ApiModelProperty(value = "优惠金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "欠款金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal debtAmount;

    @ApiModelProperty(value = "总电费金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal totalElecAmount;

    @ApiModelProperty(value = "总服务费金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal totalServiceAmount;

    @ApiModelProperty(value = "实时电压")
    private Float voltage;

    @ApiModelProperty(value = "实时电流")
    private Float current;

    @ApiModelProperty(value = "实时功率")
    private Float power;

    @ApiModelProperty(value = "电动汽车唯一标识")
    private String vin;

    @ApiModelProperty(value = "停止原因")
    private String stopReason;

    @ApiModelProperty(value = "物理卡号")
    private String physicalCardNo;

    @ApiModelProperty(value = "状态")
    private OrderStateEnum state;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "SOC")
    private Short soc;

    @ApiModelProperty(value = "充电分钟数")
    private Short chargeMinute;

    @ApiModelProperty(value = "剩余分钟数")
    private Short remainMinute;

    @ApiModelProperty(value = "结算列表")
    private List<OpenOrdersSettleVo> settles;
}
