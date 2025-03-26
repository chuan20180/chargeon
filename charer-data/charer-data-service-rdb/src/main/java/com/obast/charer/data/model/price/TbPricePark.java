package com.obast.charer.data.model.price;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PriceParkPeriodEnum;
import com.obast.charer.model.price.PricePark;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "price_park")
@ApiModel(value = "占位计价规则")
@AutoMapper(target = PricePark.class)
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonStringType.class),
})
public class TbPricePark extends BaseTenantEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private Short no;

    @ApiModelProperty(value = "充电前免费时长")
    private Integer beforeFreeMinute;

    @ApiModelProperty(value = "充电后免费时长")
    private Integer afterFreeMinute;

    @ApiModelProperty(value = "每天封顶金额")
    private BigDecimal maxAmount;

    @ApiModelProperty(value = "计费周期")
    @Convert(converter = PriceParkPeriodEnum.Converter.class)
    private PriceParkPeriodEnum period;

    @ApiModelProperty(value = "每周期单价")
    private BigDecimal price;

    @ApiModelProperty(value = "启用/停用")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;
}
