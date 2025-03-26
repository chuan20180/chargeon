package com.obast.charer.data.model.device;

import com.obast.charer.data.base.BaseAgentEntity;
import com.obast.charer.model.device.DcamParking;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "dcam_parking")
@ApiModel(value = "车位相机绑定车位信息")
@AutoMapper(target = DcamParking.class)
public class TbDcamParking extends BaseAgentEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "车位相机id")
    private String dcamId;

    @ApiModelProperty(value = "车位id")
    private String parkingId;

    @ApiModelProperty(value = "相机车位name")
    private String name;

    @ApiModelProperty(value = "相机车位no")
    private String no;

    @ApiModelProperty(value = "备注")
    private String note;

}
