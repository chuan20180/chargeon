package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParkingQueryBo extends BaseDto {

    private String stationId;

    private String name;

    private String no;

}
