package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseAgentEntity;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.parking.Parking;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "parking")
@ApiModel(value = "车位信息")
@AutoMapper(target = Parking.class)
public class TbParking extends BaseAgentEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "车位id")
    private String parkingId;

    @ApiModelProperty(value = "场站名称")
    @Type(type = "json")
    private I18nField name;

    @ApiModelProperty(value = "车位编号")
    private String no;

    @ApiModelProperty(value = "备注")
    private String note;


}
