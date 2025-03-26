package com.obast.charer.model.station;

import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationFavorite extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String stationId;

    private String customerId;
}
