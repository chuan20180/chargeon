package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.platform.RechargeItem;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值方案充值金额表单视图
 */

@ApiModel(value = "RechargeItemBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = RechargeItem.class, reverseConvertGenerate = false)
public class RechargeItemBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "充值方案id")
    @NotBlank(message = "充值方案不能为空")
    private String rechargeId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "充值金额")
    @NotBlank(message = "充值金额不能为空")
    private BigDecimal amount;

    @ApiModelProperty(value = "赠送金额")
    private BigDecimal give;

    @ApiModelProperty(value = "满减金额")
    private BigDecimal minus;

    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    @Size(max = 255, message = "描述长度不正确")
    private String note;
}
