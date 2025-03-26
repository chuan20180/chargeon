package com.obast.charer.model.platform;

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
public class RechargeItem extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String name;

    private String rechargeId;

    private BigDecimal amount;

    private BigDecimal give;

    private BigDecimal discount;

    private BigDecimal minus;

    private EnableStatusEnum status;

    private String note;
}
