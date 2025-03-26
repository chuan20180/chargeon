package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.Decimal4Serializer;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.converter.I18nToStringConverter;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.price.Price;
import com.obast.charer.model.station.Station;
import com.obast.charer.converter.StringToListConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenStationVo")
@Data
@AutoMapper(target = Station.class, uses = {I18nToStringConverter.class, StringToListConverter.class}, convertGenerate = false)
public class OpenStationVo extends BaseDto {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "位置")
    private String address;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;


    @ApiModelProperty(value = "介绍")
    private String description;

    @ApiModelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "标签")
    private List<String> tags;

    @ApiModelProperty(value = "最低价格")
    @JsonSerialize(using = Decimal4Serializer.class)
    private BigDecimal minPrice;

    @ApiModelProperty(value = "最高价格")
    @JsonSerialize(using = Decimal4Serializer.class)
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "价格列表")
    private List<Price> prices;

    @ApiModelProperty(value = "充电柱")
    private List<Charger> chargers;

    @ApiModelProperty(value = "充电枪")
    private List<OpenChargerGunVo> chargerGuns;

    @ApiModelProperty(value = "可用的总数量")
    private Integer availableGunQty;

    @ApiModelProperty(value = "可用的慢充数量")
    private Integer availableSlowGunQty;

    @ApiModelProperty(value = "不可用慢充数量")
    private Integer unavailableSlowGunQty;

    @ApiModelProperty(value = "慢充数量")
    private Integer totalSlowGunQty;

    @ApiModelProperty(value = "可用的慢充数量")
    private Integer availableQuickGunQty;

    @ApiModelProperty(value = "不可用慢充数量")
    private Integer unavailableQuickGunQty;

    @ApiModelProperty(value = "慢充数量")
    private Integer totalQuickGunQty;




}