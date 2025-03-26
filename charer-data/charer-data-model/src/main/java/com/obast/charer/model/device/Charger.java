package com.obast.charer.model.device;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnOffEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.AgentModel;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import com.obast.charer.model.price.Price;
import com.obast.charer.model.station.Station;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Charger extends AgentModel implements Id<String>, Serializable {

    private String id;

    private String productKey;

    private String stationId;

    private String priceId;

    private String name;

    private String dn;

    private String secret;

    private String model;

    private OnlineStatusEnum online;

    private Long onlineTime;

    private Long offlineTime;

    private Long lastUpdateTime;

    private EnableStatusEnum status;

    private BigDecimal lowBalance;

    private String note;

    private Price price;

    private Station station;

    private List<ChargerGun> guns;
}
