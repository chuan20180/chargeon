package com.obast.charer.data.model;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.data.base.BaseDealerEntity;
import com.obast.charer.model.ledger.LedgerSettle;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ledger_settle")
@ApiModel(value = "分帐单")
@AutoMapper(target = LedgerSettle.class)
public class TbLedgerSettle extends BaseDealerEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "流水号")
    private String tranId;

    @ApiModelProperty(value = "结算的分成金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

}
