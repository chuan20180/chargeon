package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.platform.RechargeItem;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值方案充值金额视图
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenRechargeItemVo")
@Data
@AutoMapper(target = RechargeItem.class,convertGenerate = false)
public class OpenRechargeItemVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "充值金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "赠送金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal give;

    @ApiModelProperty(value = "满减金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal minus;


    @ApiModelProperty(value = "折扣")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal discount;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;
}
