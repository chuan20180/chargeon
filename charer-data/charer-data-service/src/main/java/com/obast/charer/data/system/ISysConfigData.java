package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysConfig;
import com.obast.charer.qo.SysConfigQueryBo;

/**
 * 系统配置数据接口
 *
 * @author sjg
 */
public interface ISysConfigData  extends ICommonData<SysConfig, String>, IJPACommonData<SysConfig, SysConfigQueryBo, String> {

    SysConfig findByConfigKey(String configKey);

}
