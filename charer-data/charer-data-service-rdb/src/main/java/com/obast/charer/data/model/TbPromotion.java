package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PromotionScopeEnum;
import com.obast.charer.enums.PromotionTypeEnum;
import com.obast.charer.model.promotion.Promotion;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "promotion")
@ApiModel(value = "活动")
@AutoMapper(target = Promotion.class)
public class TbPromotion extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "适用场站")
    @Convert(converter = PromotionScopeEnum.Converter.class)
    private PromotionScopeEnum scope;



    @ApiModelProperty(value = "适用场站ids")
    @Type(type = "json")
    private List<String> stationIds;

    @ApiModelProperty(value = "活动类型")
    @Convert(converter = PromotionTypeEnum.Converter.class)
    private PromotionTypeEnum type;

    @ApiModelProperty(value = "启用时间")
    private Date startTime;

    @ApiModelProperty(value = "过期时间")
    private Date endTime;

    private String properties;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;
}
