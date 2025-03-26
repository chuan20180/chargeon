package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.CouponCodeStateEnum;
import com.obast.charer.model.coupon.CouponCode;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券表单视图
 */

@ApiModel(value = "CouponCodeBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CouponCode.class, reverseConvertGenerate = false)
public class CouponCodeBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "优惠券id")
    private String couponId;

    @ApiModelProperty(value = "优惠券编号")
    @NotBlank(message = "优惠券编号不能为空")
    private String tranId;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    @Convert(converter = CouponCodeStateEnum.Converter.class)
    private CouponCodeStateEnum state;

    @ApiModelProperty(value = "客户id")
    private String customerId;
}
