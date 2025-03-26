package com.obast.charer.openapi.dto.vo;

import com.obast.charer.model.system.SysConfig;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 参数配置视图对象 sys_config
 *
 * @author Michelle.Chung
 */
@Data
@AutoMapper(target = SysConfig.class)
public class OpenSysConfigVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 参数主键
     */
    private Long id;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;


    /**
     * 系统内置（Y是 N否）
     */
    private String configType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

}
