package com.obast.charer.system.dto.vo.station;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.converter.StringToListConverter;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.enums.OnOffEnum;
import com.obast.charer.system.dto.vo.device.ChargerGunVo;
import com.obast.charer.system.dto.vo.device.ChargerVo;
import com.obast.charer.model.station.Station;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StationVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Station.class, uses = {StringToListConverter.class}, convertGenerate = false)
public class StationVo extends BaseDto {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "")
    private String id;

    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    private I18nField name;

    @ApiModelProperty(value = "位置")
    @ExcelProperty(value = "位置")
    private I18nField address;

    @ApiModelProperty(value = "位置信息")
    private Station.Locate locate;

    @ApiModelProperty(value = "是否开启占位费")
    private OnOffEnum parkState;

    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "介绍")
    @ExcelProperty(value = "介绍")
    private I18nField description;

    @ApiModelProperty(value = "图片")
    @ExcelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "描述")
    @ExcelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "标签")
    private I18nField tags;

    @ApiModelProperty(value = "分帐组id")
    private String dealerId;

    @ApiModelProperty(value = "充电柱")
    @ExcelProperty(value = "充电柱")
    private List<ChargerVo> chargers;

    @ApiModelProperty(value = "充电枪")
    @ExcelProperty(value = "充电枪")
    private List<ChargerGunVo> chargerGuns;

    @ApiModelProperty(value = "空闲充电枪数量")
    @ExcelProperty(value = "空闲充电枪数量")
    private Integer idleGunQty;

    @ApiModelProperty(value = "不可用充电枪数量")
    @ExcelProperty(value = "不可用充电枪数量")
    private Integer busyGunQty;

    @ApiModelProperty(value = "故障充电枪数量")
    @ExcelProperty(value = "故障充电枪数量")
    private Integer failGunQty;
}