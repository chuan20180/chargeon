package com.obast.charer.system.dto.vo.device;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.device.Charger;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Charger")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Charger.class,convertGenerate = false)
public class ChargerVo extends BaseDto {

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

    @ApiModelProperty(value = "计价规则id")
    @ExcelProperty(value = "计价规则id")
    private String priceId;

    @ApiModelProperty(value = "设备离线时间")
    @ExcelProperty(value = "设备离线时间")
    private Long offlineTime;

    @ApiModelProperty(value = "设备在线时间")
    @ExcelProperty(value = "设备在线时间")
    private Long onlineTime;

    @ApiModelProperty(value = "设备秘钥")
    @ExcelProperty(value = "设备秘钥")
    private String secret;

    @ApiModelProperty(value = "设备状态")
    @ExcelProperty(value = "设备状态")
    private OnlineStatusEnum online;

    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "充电最低余额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal lowBalance;

    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "所属充电枪")
    private List<ChargerGunVo> guns;

}
