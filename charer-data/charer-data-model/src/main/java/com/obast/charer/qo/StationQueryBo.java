package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StationQueryBo extends BaseDto {

    private String keyword;

    private String id;

    private String name;

    private String address;

    private EnableStatusEnum status;
}
