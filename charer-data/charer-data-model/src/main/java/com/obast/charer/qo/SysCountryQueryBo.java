package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysCountryQueryBo extends BaseDto {

    private String name;

    private String iso2;

    private String iso3;

    private EnableStatusEnum status;
}
