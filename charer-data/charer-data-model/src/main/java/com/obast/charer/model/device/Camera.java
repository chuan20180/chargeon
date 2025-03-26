package com.obast.charer.model.device;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.AgentModel;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import com.obast.charer.model.station.Station;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Camera extends AgentModel implements Id<String>, Serializable {

    private String id;

    private String productKey;

    private String stationId;

    private String name;

    private String dn;

    private String secret;

    private OnlineStatusEnum online;

    private Long onlineTime;

    private Long offlineTime;

    private EnableStatusEnum status;

    private Station station;

}
