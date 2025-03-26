package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.model.Storage;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@ApiModel(value = "StorageBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Storage.class, reverseConvertGenerate = false)
public class StorageBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    private String id;

    @ApiModelProperty(value = "产品id")
    @NotBlank(message = "产品不能为空")
    private String productKey;

    @ApiModelProperty(value = "设备名称")
    @Size(max = 255, message = "设备名称长度不正确")
    private String name;

    @ApiModelProperty(value = "设备dn")
    @Size(max = 255, message = "设备dn长度不正确")
    private String dn;

    @ApiModelProperty(value = "备注")
    private String note;


}
