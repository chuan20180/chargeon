package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PaymentIdentifierEnum;
import com.obast.charer.model.payment.Payment;
import com.obast.charer.converter.I18nToStringConverter;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenPaymentVo")
@Data
@AutoMapper(target = Payment.class,uses = I18nToStringConverter.class, convertGenerate = false)
public class OpenPaymentVo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "支付方式")
    private PaymentIdentifierEnum identifier;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "编号")
    private String no;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "是否默认")
    private Integer isDefault;

    @ApiModelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "描述")
    private String note;
}