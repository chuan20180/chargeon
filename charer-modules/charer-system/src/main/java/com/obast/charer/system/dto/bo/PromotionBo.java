package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PromotionScopeEnum;
import com.obast.charer.enums.PromotionTypeEnum;

import com.obast.charer.model.promotion.Promotion;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：活动表单视图
 */

@ApiModel(value = "PromotionBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Promotion.class, reverseConvertGenerate = false)
public class PromotionBo extends BaseDto {

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
    @Convert(converter = PromotionScopeEnum.Converter.class)
    private PromotionScopeEnum scope;

    @ApiModelProperty(value = "适用场站ids")
    private List<String> stationIds;

    @ApiModelProperty(value = "活动类型")
    @NotNull(message = "活动类型不能为空")
    @Convert(converter = PromotionTypeEnum.Converter.class)
    private PromotionTypeEnum type;

    @ApiModelProperty(value = "启用时间")
    @NotNull(message = "有效期起始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "启用时间")
    @NotNull(message = "有效期结束时间不能为空")
    private Date endTime;

    private String properties;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;
}
