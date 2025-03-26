package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseDealerEntity;
import com.obast.charer.enums.LedgerStateEnum;
import com.obast.charer.enums.LedgerTypeEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.ledger.Ledger;
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
@Table(name = "ledger")
@ApiModel(value = "分帐单")
@AutoMapper(target = Ledger.class)
public class TbLedger extends BaseDealerEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "分帐用户名称")
    private String dealerName;

    private String agentName;

    private String tenantName;

    @ApiModelProperty(value = "分帐类型")
    @Convert(converter = LedgerTypeEnum.Converter.class)
    private LedgerTypeEnum type;

    @ApiModelProperty(value = "流水号")
    private String tranId;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单号")
    private String orderTranId;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "场站名称")
    @Type(type = "json")
    private I18nField stationName;

    @ApiModelProperty(value = "场站地址")
    @Type(type = "json")
    private I18nField stationAddress;

    @ApiModelProperty(value = "设备dn")
    private String chargerDn;

    @ApiModelProperty(value = "枪id")
    private String gunNo;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "计损总电量")
    private float totalQuantity;

    @ApiModelProperty(value = "金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "充电分钟数")
    private Short chargeMinute;

    @ApiModelProperty(value = "结算的金额")
    private BigDecimal settledAmount;

    @ApiModelProperty(value = "结算的电费金额")
    private BigDecimal settledElecAmount;

    @ApiModelProperty(value = "结算的服务费金额")
    private BigDecimal settledServiceAmount;

    @ApiModelProperty(value = "结算的占位费金额")
    private BigDecimal settledParkAmount;

    @ApiModelProperty(value = "分成结算id")
    private String ledgerSettleId;

    @ApiModelProperty(value = "分成结算成员id")
    private String ledgerSettleDealerId;

    @ApiModelProperty(value = "状态")
    @Convert(converter = LedgerStateEnum.Converter.class)
    private LedgerStateEnum state;

    @ApiModelProperty(value = "结算的分成金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "结算的分成占比")
    private BigDecimal percent;
}
