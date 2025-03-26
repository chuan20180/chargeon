package com.obast.charer.model.system;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysAgentStationDealer extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String agentId;

    private String agentStationId;

    private String dealerId;

    private BigDecimal percent;

    private EnableStatusEnum status;

    private String note;
}
