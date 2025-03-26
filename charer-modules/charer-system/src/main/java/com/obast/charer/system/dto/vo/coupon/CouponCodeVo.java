package com.obast.charer.system.dto.vo.coupon;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.CouponApplyEnum;
import com.obast.charer.enums.CouponCodeStateEnum;
import com.obast.charer.enums.CouponScopeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.coupon.CouponCode;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
 * @ Description：优惠券视图
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CouponCodeVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CouponCode.class,convertGenerate = false)
public class CouponCodeVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "优惠券池id")
    private String couponId;

    @ApiModelProperty(value = "优惠券编号")
    private String tranId;

    @ApiModelProperty(value = "适用场站")
    private CouponScopeEnum scope;

    @ApiModelProperty(value = "适用场站ids")
    private List<String> stationIds;

    @ApiModelProperty(value = "抵扣限制")
    private CouponApplyEnum apply;

    @ApiModelProperty(value = "优惠券金额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "启用时间")
    private Date enableTime;

    @ApiModelProperty(value = "过期时间")
    private Date expireTime;

    @ApiModelProperty(value = "未使用/已使用")
    private CouponCodeStateEnum state;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "客户用户名")
    private String userName;

    @ApiModelProperty(value = "适用场站名称")
    private String stationNames;
}
