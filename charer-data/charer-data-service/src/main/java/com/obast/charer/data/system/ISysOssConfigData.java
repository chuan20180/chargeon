package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysOssConfig;
import com.obast.charer.qo.SysOssConfigQueryBo;

/**
 * 操作日志数据接口
 *
 * @author sjg
 */
public interface ISysOssConfigData extends ICommonData<SysOssConfig, String>, IJPACommonData<SysOssConfig, SysOssConfigQueryBo, String> {

    SysOssConfig add(SysOssConfig sysOssConfig);

    SysOssConfig update(SysOssConfig sysOssConfig);


    SysOssConfig findByConfigKey(String configKey);

}
