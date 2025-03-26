package com.obast.charer.system.dto.vo.device;

import com.obast.charer.model.BaseModel;
import com.obast.charer.model.device.DcamParking;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DcamParkingVo")
@Data
@AutoMapper(target = DcamParking.class,convertGenerate = false)
public class DcamParkingVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "车位相机id")
    private String dcamId;

    @ApiModelProperty(value = "车位id")
    private String parkingId;

    @ApiModelProperty(value = "车位名称")
    private String name;

    @ApiModelProperty(value = "车位编号")
    private String no;

    @ApiModelProperty(value = "备注")
    private String note;

}
