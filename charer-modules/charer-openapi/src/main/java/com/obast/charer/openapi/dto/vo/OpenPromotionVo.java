package com.obast.charer.openapi.dto.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.converter.I18nToStringConverter;
import com.obast.charer.converter.StringToListConverter;
import com.obast.charer.enums.AdsTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PromotionScopeEnum;
import com.obast.charer.enums.PromotionTypeEnum;
import com.obast.charer.model.ads.Ads;
import com.obast.charer.model.promotion.Promotion;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenPromotionVo")
@Data
@AutoMapper(target = Promotion.class,convertGenerate = false)
public class OpenPromotionVo extends BaseDto {
    private static final long serialVersionUID = -1L;

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

}