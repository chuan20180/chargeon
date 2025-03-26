package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.system.model.vo.ChargerLog;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "DeviceLogQueryBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ChargerLog.class, reverseConvertGenerate = false)
public class ChargerLogQueryBo extends BaseDto {

    @ApiModelProperty(value="设备充电桩id")
    private String chargerId;

    @ApiModelProperty(value="类型")
    private String type;

    @ApiModelProperty(value="属性名")
    private String identifier;
}
