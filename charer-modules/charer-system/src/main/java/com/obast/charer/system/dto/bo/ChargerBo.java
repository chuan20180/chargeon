package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.device.Charger;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@ApiModel(value = "ChargerBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Charger.class, reverseConvertGenerate = false)
public class ChargerBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    private String id;

    @ApiModelProperty(value = "场站id")
    @NotBlank(message = "场站不能为空")
    private String stationId;

    @ApiModelProperty(value = "计价规则id")
    @NotBlank(message = "计价规则不能为空")
    private String priceId;

    @ApiModelProperty(value = "产品id")
    @NotBlank(message = "产品不能为空")
    private String productKey;

    @ApiModelProperty(value = "设备名称")
    @Size(max = 255, message = "设备名称长度不正确")
    private String name;

    @ApiModelProperty(value = "设备dn")
    @Size(max = 255, message = "设备dn长度不正确")
    private String dn;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "备注")
    private String note;

}
