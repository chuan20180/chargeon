package com.obast.charer.model.refund;

import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refund extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String tranId;

    private String customerId;

    private String userName;

    private BigDecimal amount;

    private Date refundTime;

    private RefundStateEnum state;

    private String note;
}