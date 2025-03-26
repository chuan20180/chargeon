package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.model.product.Product;
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
@ApiModel(value = "产品")
@Table(name = "product")
@AutoMapper(target = Product.class)
public class TbProduct extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "产品id")
    private String id;

    @ApiModelProperty(value = "产品key")
    private String productKey;

    @ApiModelProperty(value = "产品密钥")
    private String productSecret;

    @ApiModelProperty(value = "协议key")
    private String protocolKey;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "厂家")
    private String manufacturer;

    @ApiModelProperty(value = "品类")
    private String category;

    @ApiModelProperty(value = "产品类型")
    @Convert(converter = ProductTypeEnum.Converter.class)
    private ProductTypeEnum type;

    @ApiModelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "保活时长（秒）")
    private Long keepAliveTime;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "属性")
    private String properties;

}
