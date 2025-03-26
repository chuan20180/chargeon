package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.system.SysCountry;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@ApiModel(value = "StationBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysCountry.class)
public class SysCountryBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "名称")
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
    @NotNull(message = "状态不能为空")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;

}