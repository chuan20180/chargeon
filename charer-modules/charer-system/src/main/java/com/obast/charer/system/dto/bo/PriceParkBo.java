package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PriceParkPeriodEnum;
import com.obast.charer.model.price.PricePark;
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
 * @ Description：计价规则表单视图
 */

@ApiModel(value = "PriceParkBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PricePark.class, reverseConvertGenerate = false)
public class PriceParkBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @NotBlank(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    @Size(max = 255, message = "名称长度不能超过{max}个字符")
    private String name;


    @ApiModelProperty(value = "编号")
    private Short no;

    @ApiModelProperty(value = "充电前免费时长")
    @NotNull(message = "充电前免费时长不能为空")
    private Integer beforeFreeMinute;

    @ApiModelProperty(value = "充电后免费时长")
    @NotNull(message = "充电后免费时长不能为空")
    private Integer afterFreeMinute;

    @ApiModelProperty(value = "每天封顶金额")
    @NotNull(message = "每天封顶金额长不能为空")
    private BigDecimal maxAmount;

    @ApiModelProperty(value = "计费周期")
    @NotNull(message = "计费周期不能为空")
    private PriceParkPeriodEnum period;

    @ApiModelProperty(value = "每周期单价")
    @NotNull(message = "每周期单价不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;
}
