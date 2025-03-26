package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.RechargeTypeEnum;
import com.obast.charer.model.platform.Recharge;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值方案表单视图
 */

@ApiModel(value = "RechargeBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Recharge.class, reverseConvertGenerate = false)
public class RechargeBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "方案类型")
    @NotNull(message = "方案类型不能为空")
    @Convert(converter = RechargeTypeEnum.Converter.class)
    private RechargeTypeEnum type;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    @Size(max = 255, message = "名称长度不能超过{max}个字符")
    private String name;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    @Size(max = 255, message = "描述长度不正确")
    private String note;
}
