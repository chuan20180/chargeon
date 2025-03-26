package com.obast.charer.system.dto.bo;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.model.refund.Refund;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值记录表单视图
 */

@ApiModel(value = "RefundBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Refund.class, reverseConvertGenerate = false)
public class RefundBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "客户id")
    @NotBlank(message = "客户不能为空")
    private String customerId;

    @ApiModelProperty(value = "退款金额")
    @NotBlank(message = "退款金额不能为空")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "描述")
    private String note;
}
