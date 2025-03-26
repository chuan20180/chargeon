package com.obast.charer.data.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.model.refund.RefundBalance;
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
@Table(name = "refund_balance")
@ApiModel(value = "退款记录")
@AutoMapper(target = RefundBalance.class)
public class TbRefundBalance extends BaseTenantEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "退款id")
    private String refundId;

    @ApiModelProperty(value = "充值记录id")
    private String topupId;

    @ApiModelProperty(value = "流水号")
    private String tranId;

    @ApiModelProperty(value = "退款金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "退款状态")
    @Convert(converter = RefundStateEnum.Converter.class)
    private RefundStateEnum state;

    @ApiModelProperty(value = "退款成功时间")
    private Date successTime;

    @ApiModelProperty(value = "用户收款账号")
    private String userReceivedAccount;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "支付id")
    private String payId;
}
