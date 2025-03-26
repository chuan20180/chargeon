package com.obast.charer.system.dto.vo.parking;

import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.parking.Parking;
import com.obast.charer.model.station.Station;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ParkingVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Parking.class,convertGenerate = false)
public class ParkingVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String id;

    @ApiModelProperty(value = "充电场id")
    @ExcelProperty(value = "充电场id")
    private String stationId;

    @ApiModelProperty(value = "场站名称")
    @ExcelProperty(value = "场站名称")
    private I18nField name;

    @ApiModelProperty(value = "编号")
    @ExcelProperty(value = "编号")
    private String no;

    @ApiModelProperty(value = "描述")
    @ExcelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "所属充电场信息")
    private Station station;
}
