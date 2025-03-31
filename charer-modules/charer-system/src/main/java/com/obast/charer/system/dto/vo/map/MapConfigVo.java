package com.obast.charer.system.dto.vo.map;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;

import com.obast.charer.model.map.MapConfig;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MapConfigVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MapConfig.class)
public class MapConfigVo extends BaseDto {

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "地图提供商名称")
    @ExcelProperty(value = "地图提供商名称")
    private String name;

    @ApiModelProperty(value = "标识符")
    @ExcelProperty(value = "标识符")
    private String identifier;

    @ApiModelProperty(value = "属性")
    private String properties;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;
}