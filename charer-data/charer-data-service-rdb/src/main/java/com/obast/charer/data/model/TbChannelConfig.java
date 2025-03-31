package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.ChannelIdentifierEnum;
import com.obast.charer.model.notify.ChannelConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "channel_config")
@ApiModel(value = "通道配置")
@AutoMapper(target = ChannelConfig.class)
public class TbChannelConfig extends BaseEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "通道配置id")
    private String id;

    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    @ApiModelProperty(value = "通道配置名称")
    private String name;

    @ApiModelProperty(value = "识别符")
    @Convert(converter = ChannelIdentifierEnum.Converter.class)
    public ChannelIdentifierEnum identifier;

    @ApiModelProperty(value = "配置参数")
    @Column(columnDefinition = "TEXT")
    private String properties;

    @ApiModelProperty(value = "描述")
    private String note;
}
