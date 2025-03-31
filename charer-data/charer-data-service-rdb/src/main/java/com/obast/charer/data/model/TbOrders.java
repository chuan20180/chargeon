package com.obast.charer.data.model;

import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.common.model.dto.PriceProperties;
import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.*;
import com.obast.charer.model.order.Orders;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
@ApiModel(value = "充电订单")
@AutoMapper(target = Orders.class)
public class TbOrders extends BaseEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "充电扣款类型")
    @Convert(converter = ChargePayTypeEnum.Converter.class)
    private ChargePayTypeEnum chargePayType;

    @ApiModelProperty(value = "充电启动方式")
    @Convert(converter = ChargeStartTypeEnum.Converter.class)
    private ChargeStartTypeEnum chargeStartType;

    @ApiModelProperty(value = "充电停止方式")
    @Convert(converter = ChargeStopTypeEnum.Converter.class)
    private ChargeStopTypeEnum chargeStopType;


    @ApiModelProperty(value = "客户用户id")
    private String customerId;

    @ApiModelProperty(value = "客户登陆id")
    private String customerLoginId;

    @ApiModelProperty(value = "客户用户名")
    private String userName;

    @ApiModelProperty(value = "设备名称")
    private String chargerName;

    @ApiModelProperty(value = "设备dn")
    private String chargerDn;

    @ApiModelProperty(value = "枪no")
    private String gunNo;

    @ApiModelProperty(value = "价格id")
    private String priceId;

    @ApiModelProperty(value = "分时电价")
    @Type(type = "json")
    private PriceProperties priceProperties;

    @ApiModelProperty(value = "订单号")
    private String tranId;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "场站名称")
    @Type(type = "json")
    private I18nField stationName;

    @ApiModelProperty(value = "场站地址")
    @Type(type = "json")
    private I18nField stationAddress;

    @ApiModelProperty(value = "交易类型")
    @Convert(converter = OrderTranTypeEnum.Converter.class)
    private OrderTranTypeEnum tranType;

    @ApiModelProperty(value = "交易时间")
    private Date tranTime;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "尖单价")
    private Float sharpPrice;

    @ApiModelProperty(value = "尖电量")
    private Float sharpQty;

    @ApiModelProperty(value = "计损尖电量")
    private Float sharpQuantity;

    @ApiModelProperty(value = "尖金额")
    private BigDecimal sharpAmount;

    @ApiModelProperty(value = "峰单价")
    private Float peakPrice;

    @ApiModelProperty(value = "峰电量")
    private Float peakQty;

    @ApiModelProperty(value = "计损峰电量")
    private Float peakQuantity;

    @ApiModelProperty(value = "峰金额")
    private BigDecimal peakAmount;

    @ApiModelProperty(value = "平单价")
    private Float flatPrice;

    @ApiModelProperty(value = "平电量")
    private Float flatQty;

    @ApiModelProperty(value = "计损平电量")
    private Float flatQuantity;

    @ApiModelProperty(value = "平金额")
    private BigDecimal flatAmount;

    @ApiModelProperty(value = "谷单价")
    private Float valleyPrice;

    @ApiModelProperty(value = "谷电量")
    private Float valleyQty;

    @ApiModelProperty(value = "计损谷电量")
    private Float valleyQuantity;

    @ApiModelProperty(value = "谷金额")
    private BigDecimal valleyAmount;

    @ApiModelProperty(value = "电表总起值")
    private Float quantityStart;

    @ApiModelProperty(value = "电表总止值")
    private Float quantityEnd;

    @ApiModelProperty(value = "总电量")
    private Float totalQty;

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

    @ApiModelProperty(value = "占位id")
    private String parkId;

    @ApiModelProperty(value = "计损总电量")
    private Float totalQuantity;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "电费金额")
    private BigDecimal elecAmount;

    @ApiModelProperty(value = "服务费金额")
    private BigDecimal serviceAmount;

    @ApiModelProperty(value = "状态")
    @Convert(converter = OrderStateEnum.Converter.class)
    private OrderStateEnum state;

    @ApiModelProperty(value = "分成")
    @Convert(converter = OrderDealEnum.Converter.class)
    private OrderDealEnum deal;

    @ApiModelProperty(value = "通知")
    @Convert(converter = OrderNotifyEnum.Converter.class)
    private OrderNotifyEnum notify;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "SOC")
    private Short soc;

    @ApiModelProperty(value = "充电分钟数")
    private Short chargeMinute;

    @ApiModelProperty(value = "剩余分钟数")
    private Short remainMinute;
}
