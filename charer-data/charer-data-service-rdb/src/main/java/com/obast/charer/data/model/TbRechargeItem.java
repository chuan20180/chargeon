package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.platform.RechargeItem;
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
@Table(name = "recharge_item")
@ApiModel(value = "充值方案项目")
@AutoMapper(target = RechargeItem.class)
public class TbRechargeItem extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "充值方案id")
    private String rechargeId;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal amount;


    @ApiModelProperty(value = "赠送金额")
    private BigDecimal give;

    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;

    @ApiModelProperty(value = "满减金额")
    private BigDecimal minus;

    @ApiModelProperty(value = "启用/停用")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;
}
