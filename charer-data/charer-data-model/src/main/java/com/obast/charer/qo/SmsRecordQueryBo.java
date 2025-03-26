package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SmsRecordQueryBo extends BaseDto {

    private String id;

    public String mobile;

    public Integer result;

    public String identifier;

    public String type;
}
