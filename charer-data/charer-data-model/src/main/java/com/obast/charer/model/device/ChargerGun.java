package com.obast.charer.model.device;

import com.obast.charer.enums.ChargerGunCurrentEnum;
import com.obast.charer.enums.ChargerGunSpeedEnum;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargerGun extends BaseModel implements Id<String>, Serializable {

    private String id;
    private String chargerId;
    private String parkingId;
    private String no;
    private Integer back;
    private Integer slot;
    private ChargerGunSpeedEnum speed;
    private ChargerGunCurrentEnum current;
    private Integer power;
    private ChargerGunStateEnum state;

}
