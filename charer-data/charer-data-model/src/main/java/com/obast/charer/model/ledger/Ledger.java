package com.obast.charer.model.ledger;

import com.obast.charer.enums.LedgerStateEnum;
import com.obast.charer.enums.LedgerTypeEnum;
import com.obast.charer.common.i18n.I18nField;
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
public class Ledger extends DealerModel implements Id<String>, Serializable {

    private String id;

    private String dealerName;


    private String agentName;

    private String tenantName;


    private LedgerTypeEnum type;


    private String tranId;

    private String customerId;

    private String userName;

    private String orderId;

    private String orderTranId;

    private String stationId;

    private I18nField stationName;

    private I18nField stationAddress;

    private String chargerDn;

    private String gunNo;

    private Date startTime;

    private Date endTime;

    private BigDecimal totalQuantity;

    private BigDecimal totalAmount;

    private Short chargeMinute;

    private BigDecimal settledAmount;

    private BigDecimal settledElecAmount;

    private BigDecimal settledServiceAmount;

    private BigDecimal settledParkAmount;

    private BigDecimal amount;

    private BigDecimal percent;

    private String ledgerSettleId;

    private String ledgerSettleDealerId;

    private LedgerStateEnum state;
}
