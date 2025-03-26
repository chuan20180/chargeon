package com.obast.charer.system.dto.vo.ledger;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.common.Decimal4Serializer;
import com.obast.charer.enums.LedgerStateEnum;
import com.obast.charer.enums.LedgerTypeEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.DealerModel;
import com.obast.charer.model.ledger.Ledger;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "LedgerVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Ledger.class,convertGenerate = false)
public class LedgerVo extends DealerModel {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "分帐类型")
    private LedgerTypeEnum type;

    @ApiModelProperty(value = "分帐用户名称")
    private String dealerName;

    @ApiModelProperty(value = "分帐用户名称")
    private String tenantName;

    @ApiModelProperty(value = "分帐用户名称")
    private String agentName;

    @ApiModelProperty(value = "流水号")
    @ExcelProperty(value = "流水号")
    private String tranId;

    @ApiModelProperty(value = "客户id")
    @ExcelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "用户名")
    @ExcelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "订单id")
    @ExcelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单号")
    @ExcelProperty(value = "订单号")
    private String orderTranId;

    @ApiModelProperty(value = "场站id")
    @ExcelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "场站名称")
    @ExcelProperty(value = "场站名称")
    private I18nField stationName;

    @ApiModelProperty(value = "场站地址")
    @ExcelProperty(value = "场站地址")
    private I18nField stationAddress;

    @ApiModelProperty(value = "设备dn")
    @ExcelProperty(value = "设备dn")
    private String chargerDn;

    @ApiModelProperty(value = "枪id")
    private String gunNo;

    @ApiModelProperty(value = "开始时间")
    @ExcelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @ExcelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "计损总电量")
    @ExcelProperty(value = "计损总电量")
    @JsonSerialize(using = Decimal4Serializer.class)
    private float totalQuantity;

    @ApiModelProperty(value = "金额")
    @ExcelProperty(value = "金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "充电分钟数")
    @ExcelProperty(value = "充电分钟数")
    private Short chargeMinute;

    @ApiModelProperty(value = "结算的金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal settledAmount;

    @ApiModelProperty(value = "结算的电费金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal settledElecAmount;

    @ApiModelProperty(value = "结算的服务费金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal settledServiceAmount;

    @ApiModelProperty(value = "结算的占位费金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal settledParkAmount;

    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态")
    private LedgerStateEnum state;

    @ApiModelProperty(value = "结算的分成金额")
    @ExcelProperty(value = "结算的分成金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "结算的分成占比")
    @ExcelProperty(value = "结算的分成占比")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal percent;

}
