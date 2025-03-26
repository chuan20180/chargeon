package com.obast.charer.data.model.device;

import com.obast.charer.data.base.BaseAgentEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnOffEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.device.Charger;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicUpdate(value = false)
@Table(name = "charger")
@ApiModel(value = "设备信息")
@AutoMapper(target = Charger.class)
public class TbCharger extends BaseAgentEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "计价规则id")
    private String priceId;

    @ApiModelProperty(value = "产品key")
    private String productKey;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备dn")
    private String dn;

    @ApiModelProperty(value = "设备密钥")
    private String secret;

    @ApiModelProperty(value = "设备状态")
    @Convert(converter = OnlineStatusEnum.Converter.class)
    private OnlineStatusEnum online;

    @ApiModelProperty(value = "启用/停用")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "充电最低余额")
    private BigDecimal lowBalance;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "设备在线时间")
    private Long onlineTime;

    @ApiModelProperty(value = "设备离线时间")
    private Long offlineTime;

    @ApiModelProperty(value = "设备上次更新时间")
    private Long lastUpdateTime;
}