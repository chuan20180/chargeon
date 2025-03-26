package com.obast.charer.system.dto.vo.station;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.model.station.StationFavorite;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StationFavoriteVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StationFavorite.class)
public class StationFavoriteVo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "客户id")
    @ExcelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "场站id")
    @ExcelProperty(value = "场站id")
    private String stationId;
}