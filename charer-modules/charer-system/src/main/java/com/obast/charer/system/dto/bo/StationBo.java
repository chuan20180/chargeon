package com.obast.charer.system.dto.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnOffEnum;
import com.obast.charer.model.station.Station;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@ApiModel(value = "StationBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Station.class)
public class StationBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "名称")
    @NotNull(message = "名称不能为空")
    private I18nField name;

    @ApiModelProperty(value = "位置")
    @ExcelProperty(value = "位置")
    private I18nField address;

    @ApiModelProperty(value = "介绍")
    @ExcelProperty(value = "介绍")
    private I18nField description;

    @ApiModelProperty(value = "位置信息")
    private Station.Locate locate;

    @ApiModelProperty(value = "是否开启占位费")
    private OnOffEnum parkState;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "图片")
    @ExcelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "标签")
    @ExcelProperty(value = "标签")
    private I18nField tags;

    @ApiModelProperty(value = "描述")
    private String note;


}