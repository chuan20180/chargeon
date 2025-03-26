package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDealerQueryBo extends BaseDto {

    private String name;

    private String userName;

    private String dealId;

}
