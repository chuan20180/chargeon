package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.SysConfigDataTypeEnum;
import com.obast.charer.model.system.SysConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 参数配置表 sys_config
 *
 * @author Lion Li
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_config")
@AutoMapper(target = SysConfig.class)
public class TbSysConfig extends BaseTenantEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "参数主键")
    private String id;

    @ApiModelProperty(value = "参数名称")
    private String configName;

    /**
     * 参数键名
     */
    @ApiModelProperty(value = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @ApiModelProperty(value = "参数键值")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @ApiModelProperty(value = "系统内置（Y是 N否）")
    private String configType;

    @ApiModelProperty(value = "数据类型")
    @Convert(converter = SysConfigDataTypeEnum.Converter.class)
    private SysConfigDataTypeEnum dataType;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
