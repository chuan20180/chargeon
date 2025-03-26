package com.obast.charer.system.dto.vo.price;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PriceParkPeriodEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.price.PricePark;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
 * @ Description：计价规则视图
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PriceParkVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = PricePark.class,convertGenerate = false)
public class PriceParkVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    @ExcelProperty(value = "编号")
    private short no;

    @ApiModelProperty(value = "充电前免费时长")
    @ExcelProperty(value = "充电前免费时长")
    private Integer beforeFreeMinute;

    @ApiModelProperty(value = "充电后免费时长")
    @ExcelProperty(value = "充电后免费时长")
    private Integer afterFreeMinute;

    @ApiModelProperty(value = "每天封顶金额")
    @ExcelProperty(value = "每天封顶金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal maxAmount;

    @ApiModelProperty(value = "计费周期")
    @ExcelProperty(value = "计费周期")
    private PriceParkPeriodEnum period;

    @ApiModelProperty(value = "每周期单价")
    @ExcelProperty(value = "每周期单价")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal price;

    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    @ExcelProperty(value = "描述")
    private String note;
}
