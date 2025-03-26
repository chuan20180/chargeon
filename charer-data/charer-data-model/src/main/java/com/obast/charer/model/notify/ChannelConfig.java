package com.obast.charer.model.notify;

import com.obast.charer.enums.ChannelIdentifierEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelConfig extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String name;

    public ChannelIdentifierEnum identifier;

    private String properties;

    private String note;
}
