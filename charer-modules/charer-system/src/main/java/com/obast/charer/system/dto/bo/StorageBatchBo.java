package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.model.Storage;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;


@ApiModel(value = "StorageBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Storage.class, reverseConvertGenerate = false)
public class StorageBatchBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "产品id")
    @NotBlank(message = "产品不能为空")
    private String productKey;

    @ApiModelProperty(value = "设备名称")
    private String nameFormat;

    @ApiModelProperty(value = "设备dn")

    private Integer qty;

}
