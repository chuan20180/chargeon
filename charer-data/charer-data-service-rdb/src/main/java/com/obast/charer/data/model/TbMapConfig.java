package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.map.MapConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "map_config")
@AutoMapper(target = MapConfig.class)
public class TbMapConfig extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "参数主键")
    private String id;

    /**
     * 租户编号
     */
    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    private String name;

    public String identifier;

    private String properties;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

}
