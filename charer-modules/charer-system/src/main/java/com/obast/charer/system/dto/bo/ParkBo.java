package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.ParkStateEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.park.Park;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "ParkBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Park.class)
public class ParkBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @ApiModelProperty(value = "订单号")
    private String tranId;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "场站名称")
    @Type(type = "json")
    private I18nField stationName;

    @ApiModelProperty(value = "场站地址")
    @Type(type = "json")
    private I18nField stationAddress;

    @ApiModelProperty(value = "车位id")
    private String parkingId;

    @ApiModelProperty(value = "车位编号")
    private String parkingNo;

    @ApiModelProperty(value = "车位名称")
    @Type(type = "json")
    private I18nField parkingName;

    @ApiModelProperty(value = "车牌")
    private String carPlate;

    @ApiModelProperty(value = "车牌类型")
    private String carType;

    @ApiModelProperty(value = "入场时间")
    private Date inTime;

    @ApiModelProperty(value = "出场时间")
    private Date outTime;

    @ApiModelProperty(value = "状态")
    private ParkStateEnum state;

    @ApiModelProperty(value = "备注")
    private String note;
}