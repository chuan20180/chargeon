package com.obast.charer.system.dto.vo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.system.SysCountry;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysCountryVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysCountry.class)
public class SysCountryVo extends BaseDto {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "")
    private String id;

    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    private I18nField name;

    @ApiModelProperty(value = "Iso2")
    private String iso2;

    @ApiModelProperty(value = "ISO3")
    private String iso3;

    @ApiModelProperty(value = "国际区号")
    private String tel;

    @ApiModelProperty(value = "国际区号正则")
    private String telRule;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;

}