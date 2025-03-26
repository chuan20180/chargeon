package com.obast.charer.model.device;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.enums.PmscDirectEnum;
import com.obast.charer.model.AgentModel;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pmsc extends AgentModel implements Id<String>, Serializable {

    private String id;

    private String productKey;

    private String stationId;

    private PmscDirectEnum direct;

    private String name;

    private String dn;

    private String secret;

    private OnlineStatusEnum online;

    private Long onlineTime;

    private Long offlineTime;

    private EnableStatusEnum status;
}
