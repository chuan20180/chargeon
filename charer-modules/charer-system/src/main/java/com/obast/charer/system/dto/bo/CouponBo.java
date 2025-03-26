package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.CouponAcceptEnum;
import com.obast.charer.enums.CouponApplyEnum;
import com.obast.charer.enums.CouponScopeEnum;
import com.obast.charer.model.coupon.Coupon;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券表单视图
 */

@ApiModel(value = "CouponBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Coupon.class, reverseConvertGenerate = false)
public class CouponBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    @Size(max = 255, message = "名称长度不能超过{max}个字符")
    private String name;

    @ApiModelProperty(value = "适用场站")
    @NotNull(message = "适用场站不能为空")
    @Convert(converter = CouponScopeEnum.Converter.class)
    private CouponScopeEnum scope;

    @ApiModelProperty(value = "适用场站ids")
    private List<String> stationIds;

    @ApiModelProperty(value = "抵扣限制")
    @NotNull(message = "抵扣限制不能为空")
    @Convert(converter = CouponApplyEnum.Converter.class)
    private CouponApplyEnum apply;

    @ApiModelProperty(value = "接收用户")
    @NotNull(message = "接收用户不能为空")
    @Convert(converter = CouponAcceptEnum.Converter.class)
    private CouponAcceptEnum accept;

    @ApiModelProperty(value = "适用用户ids")
    private String acceptIds;

    @NotBlank(message = "优惠券金额不能为空")
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "启用时间")
    private Date enableTime;

    @ApiModelProperty(value = "过期时间")
    @NotNull(message = "过期时间不能为空")
    private Date expireTime;

    @ApiModelProperty(value = "描述")
    private String note;
}
