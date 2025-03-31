package com.obast.charer.model.refund;

import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundBalance extends BaseModel implements Id<String>, Serializable {

    private String id;

    private String refundId;

    private String topupId;

    private String tranId;

    private BigDecimal amount;

    private RefundStateEnum state;

    private Date successTime;

    private String userReceivedAccount;

    private String note;

    private String payId;
}