package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DcamParkingQueryBo extends BaseDto {

    private String dcamId;

    private String parkingId;
}
