package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.CouponApplyEnum;
import com.obast.charer.enums.CouponCodeStateEnum;
import com.obast.charer.enums.CouponScopeEnum;
import com.obast.charer.model.coupon.CouponCode;
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
@Table(name = "coupon_code")
@ApiModel(value = "优惠券")
@AutoMapper(target = CouponCode.class)
public class TbCouponCode extends BaseTenantEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "优惠券id")
    private String couponId;

    @ApiModelProperty(value = "优惠券编号")
    private String tranId;

    @ApiModelProperty(value = "适用场站")
    @Convert(converter = CouponScopeEnum.Converter.class)
    private CouponScopeEnum scope;

    @ApiModelProperty(value = "适用场站ids")
    @Type(type = "json")
    private List<String> stationIds;

    @ApiModelProperty(value = "抵扣限制")
    @Convert(converter = CouponApplyEnum.Converter.class)
    private CouponApplyEnum apply;

    @ApiModelProperty(value = "优惠券金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "剩余金额")
    private BigDecimal remainedAmount;

    @ApiModelProperty(value = "启用时间")
    private Date enableTime;

    @ApiModelProperty(value = "过期时间")
    private Date expireTime;

    @ApiModelProperty(value = "未使用/已使用")
    @Convert(converter = CouponCodeStateEnum.Converter.class)
    private CouponCodeStateEnum state;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "客户用户名")
    private String userName;
}