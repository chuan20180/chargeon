package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PmscDirectEnum;
import com.obast.charer.model.device.Pmsc;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@ApiModel(value = "DcamBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Pmsc.class, reverseConvertGenerate = false)
public class PmscBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    private String id;

    @ApiModelProperty(value = "产品Key")
    @NotBlank(message = "产品不能为空")
    private String productKey;

    @ApiModelProperty(value = "设备名称")
    @Size(max = 255, message = "设备名称长度不正确")
    private String name;

    @ApiModelProperty(value = "设备dn")
    @Size(max = 255, message = "设备dn长度不正确")
    private String dn;

    @ApiModelProperty(value = "场站id")
    @NotBlank(message = "场站不能为空")
    private String stationId;

    @ApiModelProperty(value = "方向")
    private PmscDirectEnum direct;

    @ApiModelProperty(value = "设备秘钥")
    private String secret;

    @ApiModelProperty(value = "设备地址")
    private String address;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "备注")
    private String note;


}
