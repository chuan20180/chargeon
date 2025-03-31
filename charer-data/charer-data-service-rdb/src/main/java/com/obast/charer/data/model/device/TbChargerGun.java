package com.obast.charer.data.model.device;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.ChargerGunCurrentEnum;
import com.obast.charer.enums.ChargerGunSpeedEnum;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.model.device.ChargerGun;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "charger_gun")
@ApiModel(value = "充电枪")
@AutoMapper(target = ChargerGun.class)
public class TbChargerGun extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "充电桩id")
    private String chargerId;

    @ApiModelProperty(value = "车位id")
    private String parkingId;

    @ApiModelProperty(value = "枪号")
    private String no;

    @ApiModelProperty(value = "是否归位")
    private Integer back;

    @ApiModelProperty(value = "是否插枪")
    private Integer slot;

    @ApiModelProperty(value = "速度")
    @Convert(converter = ChargerGunSpeedEnum.Converter.class)
    private ChargerGunSpeedEnum speed;

    @ApiModelProperty(value = "类型")
    @Convert(converter = ChargerGunCurrentEnum.Converter.class)
    private ChargerGunCurrentEnum current;

    @ApiModelProperty(value = "类型")
    private Integer power;

    @ApiModelProperty(value = "执行状态")
    @Convert(converter = ChargerGunStateEnum.Converter.class)
    private ChargerGunStateEnum state;

}
