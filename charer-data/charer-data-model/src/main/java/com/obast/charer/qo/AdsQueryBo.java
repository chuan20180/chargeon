package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.AdsTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdsQueryBo extends BaseDto {


    private String id;

    private String name;

    private AdsTypeEnum type;

    private EnableStatusEnum status;
}
