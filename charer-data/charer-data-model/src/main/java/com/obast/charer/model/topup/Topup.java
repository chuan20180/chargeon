package com.obast.charer.model.topup;

import com.obast.charer.enums.*;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topup extends TenantModel implements Id<String>, Serializable {

    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    private String customerId;

    private String userName;

    private String tranId;

    private RechargeTypeEnum rechargeType;

    private TopupSourceEnum source;

    private BigDecimal give;

    private BigDecimal minus;

    private BigDecimal discount;

    private BigDecimal topupAmount;

    private BigDecimal arrivalAmount;

    private BigDecimal paidAmount;

    private BigDecimal refundedAmount;

    private LockEnum refundLocked;

    private String paymentIdentifier;

    private String paymentName;

    private Date topupTime;

    private Date payTime;

    private String payId;

    private String bankType;

    private String tradeStateDesc;

    private String tradeType;

    private TopupStateEnum state;

    private String note;
}