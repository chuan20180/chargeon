package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysAgentStation;
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
@Table(name = "sys_agent_station")
@ApiModel(value = "代理商绑定场站信息")
@AutoMapper(target = SysAgentStation.class)
public class TbSysAgentStation extends BaseTenantEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "代理商id")
    private String agentId;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "备注")
    private String note;
}