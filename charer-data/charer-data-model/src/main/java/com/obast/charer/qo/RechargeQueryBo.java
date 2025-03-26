package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.RechargeTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class RechargeQueryBo extends BaseDto {

    private String id;

    private String name;

    private RechargeTypeEnum type;


    private EnableStatusEnum status;
}
