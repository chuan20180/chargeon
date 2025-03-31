package com.obast.charer.data.model.price;

import com.obast.charer.common.enums.PriceTypeEnum;
import com.obast.charer.common.model.dto.PriceProperties;
import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.price.Price;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "price")
@ApiModel(value = "计价规则")
@AutoMapper(target = Price.class)
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonStringType.class),
})

public class TbPrice extends BaseEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private Short no;

    @ApiModelProperty(value = "计价方式")
    @Convert(converter = PriceTypeEnum.Converter.class)
    private PriceTypeEnum type;

    @ApiModelProperty(value = "启用/停用")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "分时电价")
    @Type(type = "json")
    private PriceProperties properties;

}
