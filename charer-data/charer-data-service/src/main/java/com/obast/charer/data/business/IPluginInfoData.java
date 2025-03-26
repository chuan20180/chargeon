package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.plugin.PluginInfo;
import com.obast.charer.qo.PluginInfoQueryBo;

/**
 * 插件信息接口
 *
 * @author sjg
 */
public interface IPluginInfoData  extends ICommonData<PluginInfo, String>, IJPACommonData<PluginInfo, PluginInfoQueryBo, String> {

    /**
     * 按插件包id取插件信息
     *
     * @param pluginId 插件包id
     * @return 插件信息
     */
    PluginInfo findByPluginId(String pluginId);

}
