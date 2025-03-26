package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.model.station.StationFavorite;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@ApiModel(value = "StationFavoriteBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StationFavorite.class)
public class StationFavoriteBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "场站id")
    @NotNull(message = "场站id不能为空")
    private String stationId;

    @ApiModelProperty(value = "客户id")
    @NotNull(message = "客户id不能为空")
    private String customerId;

}