package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.model.protocol.Protocol;
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
@ApiModel(value = "协议")
@Table(name = "protocol")
@AutoMapper(target = Protocol.class)
public class TbProtocol extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "协议id")
    private String id;

    @ApiModelProperty(value = "协议key")
    private String protocolKey;


    @ApiModelProperty(value = "协议名称")
    private String name;

    @ApiModelProperty(value = "厂商")
    private String manufacturer;

    @ApiModelProperty(value = "类型")
    @Convert(converter = ProductTypeEnum.Converter.class)
    private ProductTypeEnum type;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "配置")
    private String config;

}
