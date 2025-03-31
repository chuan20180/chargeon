package com.obast.charer.system.dto.vo.coupon;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.CouponAcceptEnum;
import com.obast.charer.enums.CouponApplyEnum;
import com.obast.charer.enums.CouponScopeEnum;
import com.obast.charer.model.coupon.Coupon;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value = "CouponVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Coupon.class, convertGenerate = false)
public class CouponVo extends BaseDto {

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "适用场站")
    private CouponScopeEnum scope;

    @ApiModelProperty(value = "抵扣限制")
    private CouponApplyEnum apply;

    @ApiModelProperty(value = "适用场站ids")
    private List<String> stationIds;

    @ApiModelProperty(value = "接收用户")
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

    @ApiModelProperty(value = "适用场站名称")
    private String stationNames;
}
