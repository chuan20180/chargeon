package com.obast.charer.system.dto.vo.device;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.device.Dcam;
import com.obast.charer.model.product.Product;
import com.obast.charer.model.station.Station;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Dcam")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Dcam.class,convertGenerate = false)
public class DcamVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "产品Key")
    @ExcelProperty(value = "产品Key")
    private String productKey;

    @ApiModelProperty(value = "设备名称")
    @ExcelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备dn")
    @ExcelProperty(value = "设备dn")
    private String dn;

    @ApiModelProperty(value = "场站id")
    @ExcelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "占位计费规则")
    private String priceParkId;

    @ApiModelProperty(value = "设备状态")
    @ExcelProperty(value = "设备状态")
    private OnlineStatusEnum online;

    @ApiModelProperty(value = "设备离线时间")
    @ExcelProperty(value = "设备离线时间")
    private Long offlineTime;

    @ApiModelProperty(value = "设备在线时间")
    @ExcelProperty(value = "设备在线时间")
    private Long onlineTime;

    @ApiModelProperty(value = "设备秘钥")
    @ExcelProperty(value = "设备秘钥")
    private String secret;

    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "所属产品信息")
    private Product product;

    @ApiModelProperty(value = "所属场站信息")
    private Station station;

}
