package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.ChannelIdentifierEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.NotifyIdentifierEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotifyConfigQueryBo extends BaseDto {

    private String name;

    public NotifyIdentifierEnum identifier;

    public ChannelIdentifierEnum channelIdentifier;

    public EnableStatusEnum status;

}
