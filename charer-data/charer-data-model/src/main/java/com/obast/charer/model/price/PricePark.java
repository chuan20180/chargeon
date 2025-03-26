package com.obast.charer.model.price;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PriceParkPeriodEnum;
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
public class PricePark extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String name;

    private Short no;

    private Integer beforeFreeMinute;

    private Integer afterFreeMinute;

    private BigDecimal maxAmount;

    private PriceParkPeriodEnum period;

    private BigDecimal price;

    private EnableStatusEnum status;

    private String note;

}
