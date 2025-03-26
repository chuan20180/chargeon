package com.obast.charer.model.platform;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.RechargeTypeEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recharge extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String name;

    private RechargeTypeEnum type;

    private EnableStatusEnum status;

    private String note;

}
