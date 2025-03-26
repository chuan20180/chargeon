package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.ChargerGunCurrentEnum;
import com.obast.charer.enums.ChargerGunSpeedEnum;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.model.device.ChargerGun;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "ChargerGun")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ChargerGun.class)
public class ChargerGunBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    private String id;

    private String chargerId;

    private String parkingId;

    private String no;

    private Integer back;

    private Integer slot;

    private ChargerGunSpeedEnum speed;

    private ChargerGunCurrentEnum current;

    private Integer power;

    private ChargerGunStateEnum state;

}