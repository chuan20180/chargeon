package com.obast.charer.model.system;

import com.obast.charer.enums.SysConfigDataTypeEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * 参数配置视图对象 sys_config
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfig extends TenantModel implements Id<String>, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String configName;

    private String configKey;

    private String configValue;

    private String configType;

    private SysConfigDataTypeEnum dataType;

    private String remark;

    private Date createTime;

}
