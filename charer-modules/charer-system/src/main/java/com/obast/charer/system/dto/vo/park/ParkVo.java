package com.obast.charer.system.dto.vo.park;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.converter.StringToListConverter;
import com.obast.charer.enums.ParkStateEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.park.Park;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ParkVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Park.class, uses = {StringToListConverter.class}, convertGenerate = false)
public class ParkVo extends BaseDto {

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String tranId;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "场站名称")
    private I18nField stationName;

    @ApiModelProperty(value = "场站地址")
    private I18nField stationAddress;

    @ApiModelProperty(value = "车位id")
    private String parkingId;

    @ApiModelProperty(value = "车位编号")
    private String parkingNo;

    @ApiModelProperty(value = "车位名称")
    private I18nField parkingName;

    @ApiModelProperty(value = "车牌")
    private String carPlate;

    @ApiModelProperty(value = "车牌类型")
    private String carType;

    @ApiModelProperty(value = "入场时间")
    private Date inTime;

    @ApiModelProperty(value = "出场时间")
    private Date outTime;


    private List<String> inBgImage;
    private List<String> inFetureImage;


    private List<String> outBgImage;
    private List<String> outFetureImage;

    @ApiModelProperty(value = "状态")
    private ParkStateEnum state;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "占位时间")
    private Long occupyMinute;

    @ApiModelProperty(value = "占位时长字符串")
    private String occupyText;
}