package com.obast.charer.data.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.*;
import com.obast.charer.model.topup.Topup;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "topup")
@ApiModel(value = "充值记录")
@AutoMapper(target = Topup.class)
public class TbTopup extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "客户用户名")
    private String userName;

    @ApiModelProperty(value = "流水号")
    private String tranId;

    @ApiModelProperty(value = "充值类型")
    @Convert(converter = RechargeTypeEnum.Converter.class)
    private RechargeTypeEnum rechargeType;

    @ApiModelProperty(value = "充值来源")
    @Convert(converter = TopupSourceEnum.Converter.class)
    private TopupSourceEnum source;

    @ApiModelProperty(value = "赠送金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal give;

    @ApiModelProperty(value = "立减金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal minus;

    @ApiModelProperty(value = "服务费打折数量")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal discount;

    @ApiModelProperty(value = "充值金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal topupAmount;

    @ApiModelProperty(value = "到账金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal arrivalAmount;

    @ApiModelProperty(value = "支付金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal paidAmount;

    @ApiModelProperty(value = "已退款金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal refundedAmount;

    @ApiModelProperty(value = "退款锁定")
    @Convert(converter = LockEnum.Converter.class)
    private LockEnum refundLocked;

    @ApiModelProperty(value = "支付方式id")
    private String paymentIdentifier;

    @ApiModelProperty(value = "支付方式名称")
    private String paymentName;

    @ApiModelProperty(value = "充值时间")
    private Date topupTime;

    @ApiModelProperty(value = "付款时间")
    private Date payTime;

    @ApiModelProperty(value = "付款id")
    private String payId;

    private String bankType;

    private String tradeStateDesc;

    private String tradeType;

    @ApiModelProperty(value = "充值状态")
    @Convert(converter = TopupStateEnum.Converter.class)
    private TopupStateEnum state;



    @ApiModelProperty(value = "描述")
    private String note;

}
