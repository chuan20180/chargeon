package com.obast.charer.openapi.dto.vo;

import com.obast.charer.converter.I18nToStringConverter;
import com.obast.charer.converter.StringToListConverter;
import com.obast.charer.enums.NoticeStateEnum;
import com.obast.charer.enums.NoticeTypeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.system.Notice;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenNoticeVo")
@Data
@AutoMapper(target = Notice.class, uses = {I18nToStringConverter.class, StringToListConverter.class}, convertGenerate = false)
public class OpenNoticeVo extends BaseModel {

    private static final long serialVersionUID = -1L;

    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "类型")
    private NoticeTypeEnum type;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "状态")
    private NoticeStateEnum state;

}