package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.Storage;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;


@ApiModel(value = "StorageBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Storage.class, reverseConvertGenerate = false)
public class StorageStoreBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "产品id")
    @NotBlank(message = "场站不能为空")
    private String stationId;

    @ApiModelProperty(value = "计价规则id")
    @NotBlank(message = "计价规则不能为空")
    private String priceId;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;


    private List<String> ids;

}
