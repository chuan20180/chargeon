package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.AdsTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.ads.Ads;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@ApiModel(value = "AdsBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Ads.class)
public class AdsBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "名称")
    @NotNull(message = "名称不能为空")
    private I18nField name;

    @ApiModelProperty(value = "类型")
    @NotNull(message = "类型不能为空")
    private AdsTypeEnum type;

    @ApiModelProperty(value = "html内容")
    private I18nField content;

    @ApiModelProperty(value = "链接")
    private String link;

    @ApiModelProperty(value = "img")
    private String img;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;
}