package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.ChargerGunStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChargerGunQueryBo extends BaseDto {

    private String stationId;

    private String chargerId;

    private String no;

    private ChargerGunStateEnum state;

}
