package com.obast.charer.openapi.dto.vo;

import com.obast.charer.enums.ChargerGunCurrentEnum;
import com.obast.charer.enums.ChargerGunSpeedEnum;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.device.ChargerGun;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenChargerGun")
@Data
@AutoMapper(target = ChargerGun.class)
public class OpenChargerGunVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "充电柱id")
    private String chargerId;

    @ApiModelProperty(value = "充电枪号")
    private String no;

    @ApiModelProperty(value = "是否归位")
    private Integer back;

    @ApiModelProperty(value = "是否插枪")
    private Integer slot;

    @ApiModelProperty(value = "速度")
    private ChargerGunSpeedEnum speed;

    @ApiModelProperty(value = "类型")
    private ChargerGunCurrentEnum current;

    @ApiModelProperty(value = "类型")
    private Integer power;

    @ApiModelProperty(value = "枪状态")
    private ChargerGunStateEnum state;
}
