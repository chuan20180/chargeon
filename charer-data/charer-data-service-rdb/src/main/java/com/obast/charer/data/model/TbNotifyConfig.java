package com.obast.charer.data.model;

import com.obast.charer.common.tenant.dao.TenantAware;
import com.obast.charer.common.tenant.listener.TenantListener;
import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.NotifyIdentifierEnum;
import com.obast.charer.model.notify.NotifyConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notify_config")
@ApiModel(value = "通知配置")
@AutoMapper(target = NotifyConfig.class)
public class TbNotifyConfig extends BaseTenantEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "参数主键")
    private String id;

    @ApiModelProperty(value = "通知名称")
    private String name;

    @ApiModelProperty(value = "识别符")
    @Convert(converter = NotifyIdentifierEnum.Converter.class)
    public NotifyIdentifierEnum identifier;

    @ApiModelProperty(value = "配置参数")
    @Type(type = "json")
    private NotifyConfig.Properties properties;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    private String note;

}
