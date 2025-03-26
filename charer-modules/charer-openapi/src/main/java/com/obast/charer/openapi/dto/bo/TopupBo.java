package com.obast.charer.openapi.dto.bo;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.TopupStateEnum;
import com.obast.charer.model.topup.Topup;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值记录表单视图
 */

@ApiModel(value = "TopupBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Topup.class, reverseConvertGenerate = false)
public class TopupBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "客户id")
    @NotBlank(message = "客户不能为空")
    private String customerId;

    @ApiModelProperty(value = "充值金额")
    @NotBlank(message = "充值金额不能为空")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal amount;

    @ApiModelProperty(value = "支付方式id")
    @NotBlank(message = "支付方式不能为空")
    private String paymentId;

    @ApiModelProperty(value = "充值项目id")
    @NotBlank(message = "充值项目不能为空")
    private String rechargeItemId;

    @ApiModelProperty(value = "充值时间")
    private Date topupTime;

    @ApiModelProperty(value = "充值状态")
    @Convert(converter = TopupStateEnum.Converter.class)
    private TopupStateEnum state;

    @ApiModelProperty(value = "描述")
    private String note;
}
