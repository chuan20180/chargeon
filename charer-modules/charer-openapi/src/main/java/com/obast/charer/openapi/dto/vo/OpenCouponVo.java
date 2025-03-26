package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.CouponApplyEnum;
import com.obast.charer.enums.CouponScopeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.coupon.Coupon;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券池视图
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenCouponVo")
@Data
@AutoMapper(target = Coupon.class,convertGenerate = false)
public class OpenCouponVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "适用场站")
    private CouponScopeEnum scope;

    @ApiModelProperty(value = "抵扣限制")
    private CouponApplyEnum apply;

    @ApiModelProperty(value = "适用场站ids")
    private List<String> stationIds;

    @ApiModelProperty(value = "优惠券数量")
    private Integer qty;

    @ApiModelProperty(value = "金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "启用时间")
    private Date enableTime;

    @ApiModelProperty(value = "过期时间")
    private Date expireTime;

    @ApiModelProperty(value = "启用/停用")
    private EnableStatusEnum state;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "适用场站名称")
    private String stationNames;
}
