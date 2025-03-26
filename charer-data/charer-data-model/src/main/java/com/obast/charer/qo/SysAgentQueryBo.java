package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysAgentQueryBo extends BaseDto {

    private String name;

    private String contactPerson;

    private String contactMobile;

    private EnableStatusEnum status;

}
