package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.model.plugin.PluginInstance;

/**
 * 插件实例接口
 *
 * @author sjg
 */
public interface IPluginInstanceData extends ICommonData<PluginInstance, String> {

    /**
     * 获取插件实例
     *
     * @param mainId   主程序id
     * @param pluginId 插件包id
     * @return 插件实例
     */
    PluginInstance findInstance(String mainId, String pluginId);

}
