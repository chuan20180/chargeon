package com.obast.charer.model.system;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysAgent extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String name;

    private String contactPerson;

    private String contactMobile;

    private List<String> menuIds;

    private EnableStatusEnum status;

    private BigDecimal tenantProfitPercent;

    private String note;
}
