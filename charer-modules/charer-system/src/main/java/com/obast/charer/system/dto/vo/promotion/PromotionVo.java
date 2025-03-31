package com.obast.charer.system.dto.vo.promotion;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PromotionScopeEnum;
import com.obast.charer.enums.PromotionTypeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.promotion.Promotion;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：活动视图
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PromotionVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Promotion.class,convertGenerate = false)
public class PromotionVo extends BaseDto {

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "适用场站")
    private PromotionScopeEnum scope;

    @ApiModelProperty(value = "适用场站ids")
    private List<String> stationIds;

    @ApiModelProperty(value = "活动类型")
    private PromotionTypeEnum type;

    @ApiModelProperty(value = "启用时间")
    private Date startTime;

    @ApiModelProperty(value = "过期时间")
    private Date endTime;

    private String properties;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "适用场站名称")
    private String stationNames;
}
