package com.obast.charer.system.dto.vo.payment;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.payment.Payment;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PaymentVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Payment.class)
public class PaymentVo extends BaseDto {

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    private I18nField name;

    @ApiModelProperty(value = "支付方式")
    private String identifier;

    @ApiModelProperty(value = "描述")
    @ExcelProperty(value = "描述")
    private I18nField description;

    @ApiModelProperty(value = "编号")
    @ExcelProperty(value = "编号")
    private String no;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "是否默认")
    @ExcelProperty(value = "是否默认")
    private Integer isDefault;

    @ApiModelProperty(value = "图片")
    @ExcelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "描述")
    @ExcelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "属性")
    private String properties;
}