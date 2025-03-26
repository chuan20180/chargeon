package com.obast.charer.data.model;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.data.base.BaseDealerEntity;
import com.obast.charer.enums.LedgerSettleStateEnum;
import com.obast.charer.enums.LedgerTypeEnum;
import com.obast.charer.model.ledger.LedgerSettleDealer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ledger_settle_dealer")
@ApiModel(value = "分帐单")
@AutoMapper(target = LedgerSettleDealer.class)
public class TbLedgerSettleDealer extends BaseDealerEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "分帐类型")
    @Convert(converter = LedgerTypeEnum.Converter.class)
    private LedgerTypeEnum type;

    @ApiModelProperty(value = "结算单id")
    private String ledgerSettleId;

    @ApiModelProperty(value = "流水号")
    private String tranId;

    private String dealerName;

    private String agentName;

    private String tenantName;

    @ApiModelProperty(value = "状态")
    @Convert(converter = LedgerSettleStateEnum.Converter.class)
    private LedgerSettleStateEnum state;

    @ApiModelProperty(value = "结算的分成金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "结算时间")
    private Date settleTime;

    @ApiModelProperty(value = "打款时间")
    private Date paidTime;
}
