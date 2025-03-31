package com.obast.charer.data.model;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.CouponAcceptEnum;
import com.obast.charer.enums.CouponApplyEnum;
import com.obast.charer.enums.CouponScopeEnum;
import com.obast.charer.model.coupon.Coupon;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "coupon")
@ApiModel(value = "优惠券池")
@AutoMapper(target = Coupon.class)
public class TbCoupon extends BaseEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;


    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "适用场站")
    @Convert(converter = CouponScopeEnum.Converter.class)
    private CouponScopeEnum scope;

    @ApiModelProperty(value = "适用场站ids")
    @Type(type = "json")
    private List<String> stationIds;

    @ApiModelProperty(value = "抵扣限制")
    @Convert(converter = CouponApplyEnum.Converter.class)
    private CouponApplyEnum apply;

    @ApiModelProperty(value = "接收用户")
    @Convert(converter = CouponAcceptEnum.Converter.class)
    private CouponAcceptEnum accept;

    @ApiModelProperty(value = "适用用户ids")
    private String acceptIds;

    @ApiModelProperty(value = "金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "启用时间")
    private Date enableTime;

    @ApiModelProperty(value = "过期时间")
    private Date expireTime;

    @ApiModelProperty(value = "描述")
    private String note;
}
