package com.obast.charer.data.model.device;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.ChargerDirectiveResultEnum;
import com.obast.charer.enums.ChargerDirectiveStateEnum;
import com.obast.charer.enums.ChargerDirectiveTypeEnum;
import com.obast.charer.model.device.ChargerDirective;
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
@Table(name = "charger_directive")
@ApiModel(value = "设备指令")
@AutoMapper(target = ChargerDirective.class)
public class TbChargerDirective extends BaseTenantEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "设备dn")
    private String dn;

    @ApiModelProperty(value = "关联类型")
    @Convert(converter = ChargerDirectiveTypeEnum.Converter.class)
    private ChargerDirectiveTypeEnum type;

    @ApiModelProperty(value = "关联对象id")
    private String relateId;

    @ApiModelProperty(value = "指令序列号")
    private Integer serial;

    @ApiModelProperty(value = "指令名")
    private String directive;

    @ApiModelProperty(value = "数据")
    private String data;

    @ApiModelProperty(value = "执行状态")
    @Convert(converter = ChargerDirectiveStateEnum.Converter.class)
    private ChargerDirectiveStateEnum state;

    @ApiModelProperty(value = "执行结果")
    @Convert(converter = ChargerDirectiveResultEnum.Converter.class)
    private ChargerDirectiveResultEnum result;

    private String reason;

}
