package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysAgentStationDealer;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_agent_station_dealer")
@ApiModel(value = "代理商信合作商绑定息")
@AutoMapper(target = SysAgentStationDealer.class)
public class TbSysAgentStationDealer extends BaseTenantEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "代理商id")
    private String agentId;

    @ApiModelProperty(value = "代理商绑定合作商id")
    private String agentStationId;

    @ApiModelProperty(value = "合作商id")
    private String dealerId;

    @ApiModelProperty(value = "分成比例")
    private BigDecimal percent;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "备注")
    private String note;
}