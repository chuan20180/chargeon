package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.MessageStateEnum;
import com.obast.charer.enums.NotifyScopeEnum;
import com.obast.charer.enums.NotifyTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerNotifyQueryBo extends BaseDto {

    private String id;

    private String customerId;

    private NotifyTypeEnum type;


    private NotifyScopeEnum scope;

    private String content;

    private MessageStateEnum state;

}
