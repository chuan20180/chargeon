package com.obast.charer.model.customer;

import com.obast.charer.enums.MessageStateEnum;
import com.obast.charer.enums.NotifyScopeEnum;
import com.obast.charer.enums.NotifyTypeEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerNotify extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String customerId;

    private NotifyTypeEnum type;

    private String entityId;

    private NotifyScopeEnum scope;

    private String content;

    private MessageStateEnum state;

    private String note;

}