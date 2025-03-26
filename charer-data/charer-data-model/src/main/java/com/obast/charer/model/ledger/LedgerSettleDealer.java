package com.obast.charer.model.ledger;

import com.obast.charer.enums.LedgerSettleStateEnum;
import com.obast.charer.enums.LedgerTypeEnum;
import com.obast.charer.model.DealerModel;
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
public class LedgerSettleDealer extends DealerModel implements Id<String>, Serializable {

    private String id;

    private LedgerTypeEnum type;

    private String ledgerSettleId;

    private String tranId;

    private String dealerName;

    private String agentName;

    private String tenantName;

    private LedgerSettleStateEnum state;

    private BigDecimal amount;

    private Date settleTime;

    private Date paidTime;
}
