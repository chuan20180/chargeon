package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbPluginInfo;
import com.obast.charer.data.model.TbPluginInstance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sjg
 */
public interface PluginInstanceRepository extends JpaRepository<TbPluginInstance, Long> {

    /**
     * 按主程序id和插件id获取插件实例
     *
     * @param mainId   主程序id
     * @param pluginId 插件id
     * @return 插件实例
     */
    TbPluginInfo findByMainIdAndPluginId(String mainId, String pluginId);

}
