package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.model.device.DcamParking;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;


@ApiModel(value = "DcamParkingBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = DcamParking.class, reverseConvertGenerate = false)
public class DcamParkingBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    private String id;

    @ApiModelProperty(value = "车位相机id")
    @NotBlank(message = "车位相机不能为空")
    private String dcamId;

    @ApiModelProperty(value = "车位id")
    @NotBlank(message = "车位不能为空")
    private String parkingId;

    @ApiModelProperty(value = "车位名称")
    private String name;

    @ApiModelProperty(value = "车位编号")
    private String no;

    @ApiModelProperty(value = "备注")
    private String note;

}
