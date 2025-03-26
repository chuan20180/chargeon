package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.converter.I18nToStringConverter;
import com.obast.charer.converter.StringToListConverter;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysCountry;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenSysCountryVo")
@Data
@AutoMapper(target = SysCountry.class,uses = {I18nToStringConverter.class, StringToListConverter.class}, convertGenerate = false)
public class OpenSysCountryVo extends BaseDto {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    private String iso2;

    private String iso3;

    private String tel;

    private String telRule;

    private String icon;

    private EnableStatusEnum status;

    private String note;

}