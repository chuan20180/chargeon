package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.product.Product;
import com.obast.charer.model.station.Station;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenChargerVo")
@Data
@AutoMapper(target = Charger.class, convertGenerate = false)
public class OpenChargerVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备dn")
    private String dn;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "计价规则id")
    private String priceId;

    @ApiModelProperty(value = "设备离线时间")
    private Long offlineTime;

    @ApiModelProperty(value = "设备在线时间")
    private Long onlineTime;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "设备秘钥")
    private String secret;

    @ApiModelProperty(value = "设备状态")
    private OnlineStatusEnum online;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "充电最低余额")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal lowBalance;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "所属产品信息")
    private Product product;

    @ApiModelProperty(value = "所属场站信息")
    private Station station;

    @ApiModelProperty(value = "所属计价规则信息")
    private OpenPriceVo price;

    @ApiModelProperty(value = "所属充电枪")
    private List<OpenChargerGunVo> guns;

}
