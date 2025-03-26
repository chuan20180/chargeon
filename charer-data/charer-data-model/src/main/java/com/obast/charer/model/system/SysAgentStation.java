package com.obast.charer.model.system;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysAgentStation extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String agentId;

    private String stationId;

    private EnableStatusEnum status;

    private String note;
}
