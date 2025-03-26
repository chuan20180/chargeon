package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.model.station.StationFavorite;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenStationFavoriteVo")
@Data
@AutoMapper(target = StationFavorite.class)
public class OpenStationFavoriteVo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "场站id")
    private String stationId;
}