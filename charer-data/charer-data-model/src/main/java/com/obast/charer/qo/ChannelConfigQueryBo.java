package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.ChannelIdentifierEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelConfigQueryBo extends BaseDto {

    private String name;

    public ChannelIdentifierEnum identifier;

}
