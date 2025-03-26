package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.model.Storage;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "storage")
@ApiModel(value = "充电柱库")
@AutoMapper(target = Storage.class)
public class TbStorage extends BaseTenantEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    private String id;

    @ApiModelProperty(value = "产品key")
    private String productKey;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备dn")
    private String dn;

    @ApiModelProperty(value = "备注")
    private String note;

}