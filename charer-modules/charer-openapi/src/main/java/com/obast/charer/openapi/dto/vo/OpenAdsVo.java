package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.converter.I18nToStringConverter;
import com.obast.charer.converter.StringToListConverter;
import com.obast.charer.enums.AdsTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.ads.Ads;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenAdsVo")
@Data
@AutoMapper(target = Ads.class, uses = {I18nToStringConverter.class, StringToListConverter.class}, convertGenerate = false)
public class OpenAdsVo extends BaseDto {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private AdsTypeEnum type;

    @ApiModelProperty(value = "html内容")
    private String content;

    @ApiModelProperty(value = "链接")
    private String link;

    @ApiModelProperty(value = "img")
    private String img;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;
}