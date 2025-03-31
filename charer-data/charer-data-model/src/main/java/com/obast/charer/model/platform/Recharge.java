package com.obast.charer.model.platform;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.RechargeTypeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recharge extends BaseModel implements Id<String>, Serializable {

    private String id;

    private String name;

    private RechargeTypeEnum type;

    private EnableStatusEnum status;

    private String note;

}
