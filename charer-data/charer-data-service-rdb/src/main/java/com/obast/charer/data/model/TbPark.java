package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseAgentEntity;
import com.obast.charer.enums.ParkSettleEnum;
import com.obast.charer.enums.ParkStateEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.park.Park;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "park")
@ApiModel(value = "车辆停放记录")
@AutoMapper(target = Park.class)
public class TbPark extends BaseAgentEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String tranId;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "场站名称")
    @Type(type = "json")
    private I18nField stationName;

    @ApiModelProperty(value = "车位相机id")
    private String dcamId;

    @ApiModelProperty(value = "车位相机编号d")
    private String dcamDn;

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
    private Integer carPlateType;

    @ApiModelProperty(value = "入场时间")
    private Date inTime;

    @ApiModelProperty(value = "出场时间")
    private Date outTime;


    @ApiModelProperty(value = "inBgImage")
    @Type(type = "json")
    private List<String> inBgImage;

    @ApiModelProperty(value = "inFetureImage")
    @Type(type = "json")
    private List<String> inFetureImage;

    @ApiModelProperty(value = "outBgImage")
    @Type(type = "json")
    private List<String> outBgImage;

    @ApiModelProperty(value = "outFetureImage")
    @Type(type = "json")
    private List<String> outFetureImage;

    @ApiModelProperty(value = "状态")
    @Convert(converter = ParkStateEnum.Converter.class)
    private ParkStateEnum state;

    @ApiModelProperty(value = "结算状态")
    @Convert(converter = ParkSettleEnum.Converter.class)
    private ParkSettleEnum settle;

    @ApiModelProperty(value = "备注")
    private String note;

}