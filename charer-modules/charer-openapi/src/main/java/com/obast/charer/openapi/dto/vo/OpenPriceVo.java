package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.enums.PriceTypeEnum;
import com.obast.charer.common.model.dto.PriceFee;
import com.obast.charer.common.model.dto.PriceInterval;
import com.obast.charer.common.model.dto.PriceProperties;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.price.Price;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：计价规则视图
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenPriceVo")
@Data
@AutoMapper(target = Price.class, convertGenerate = false)
public class OpenPriceVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private short no;

    @ApiModelProperty(value = "计价方式")
    private PriceTypeEnum type;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "分时电价")
    private PriceProperties properties;

    private PriceFee currentPrice;

    @ApiModelProperty(value = "最低价格")
    private PriceFee minPrice;

    @ApiModelProperty(value = "最高价格")
    private PriceFee maxPrice;

    @ApiModelProperty(value = "价格时间列表")
    private List<PriceInterval> intervals;
}
