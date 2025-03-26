package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MapConfigQueryBo extends BaseDto {

    private String id;

    private String name;

    public String identifier;

}
