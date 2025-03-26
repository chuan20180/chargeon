package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigQueryBo extends BaseDto {

    private String id;

    private String configKey;

    private String configName;

    private String configType;

}
