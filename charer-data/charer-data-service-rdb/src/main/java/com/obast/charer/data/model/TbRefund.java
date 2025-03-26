package com.obast.charer.data.model;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.model.refund.Refund;
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
@Table(name = "refund")
@ApiModel(value = "退款记录")
@AutoMapper(target = Refund.class)
public class TbRefund extends BaseTenantEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "流水号")
    private String tranId;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "客户用户名")
    private String userName;

    @ApiModelProperty(value = "退款金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "退款时间")
    private Date refundTime;

    @ApiModelProperty(value = "退款状态")
    @Convert(converter = RefundStateEnum.Converter.class)
    private RefundStateEnum state;

    @ApiModelProperty(value = "描述")
    private String note;

}
