package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class PushConfigQueryBo extends BaseDto {

    private String id;

    private String name;

    public String identifier;

    private Map<String, String> param;
}
