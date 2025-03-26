package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;

import com.obast.charer.common.enums.PriceTypeEnum;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;

import com.obast.charer.model.price.Price;
import com.obast.charer.common.model.dto.PriceProperties;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：计价规则表单视图
 */

@ApiModel(value = "PriceBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Price.class, reverseConvertGenerate = false)
public class PriceBo extends BaseDto {

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

    @ApiModelProperty(value = "计价方式")
    @NotNull(message = "计价方式不能为空")
    @Convert(converter = PriceTypeEnum.Converter.class)
    private PriceTypeEnum type;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    @Size(max = 255, message = "描述长度不正确")
    private String note;

    @ApiModelProperty(value = "分时电价")
    private PriceProperties properties;

}
