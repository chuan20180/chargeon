package com.obast.charer.data.model;

import com.obast.charer.common.tenant.dao.TenantAware;
import com.obast.charer.common.tenant.listener.TenantListener;
import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.model.station.StationFavorite;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "station_favorite")
@ApiModel(value = "设备信息")
@AutoMapper(target = StationFavorite.class)
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "string")})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
@EntityListeners(TenantListener.class)
public class TbStationFavorite extends BaseEntity implements TenantAware {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "场站id")
    private String stationId;
}