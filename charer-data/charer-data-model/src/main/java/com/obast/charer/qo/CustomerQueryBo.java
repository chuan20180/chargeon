package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.CustomerTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerQueryBo extends BaseDto {

    private String userName;

    private String mobile;

    private String nickName;

    private CustomerTypeEnum type;

    private EnableStatusEnum status;
}
