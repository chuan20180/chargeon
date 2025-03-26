package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.RechargeTypeEnum;
import com.obast.charer.model.platform.Recharge;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "recharge")
@ApiModel(value = "充值方案")
@AutoMapper(target = Recharge.class)
public class TbRecharge extends BaseTenantEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    @ApiModelProperty(value = "方案类型")
    @Convert(converter = RechargeTypeEnum.Converter.class)
    private RechargeTypeEnum type;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "启用/停用")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;

}
