package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StationFavoriteQueryBo extends BaseDto {

    private String id;

    private String stationId;

    private String customerId;
}
