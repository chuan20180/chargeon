package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.dao.PluginInstanceRepository;
import com.obast.charer.data.business.IPluginInstanceData;
import com.obast.charer.data.model.TbPluginInstance;
import com.obast.charer.model.plugin.PluginInstance;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * @author sjg
 */
@Primary
@Service
public class PluginInstanceDataImpl implements IPluginInstanceData, IJPACommData<PluginInstance, String> {

    @Autowired
    private PluginInstanceRepository pluginInstanceRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public JpaRepository getBaseRepository() {
        return pluginInstanceRepository;
    }

    @Override
    public Class<TbPluginInstance> getJpaRepositoryClass() {
        return TbPluginInstance.class;
    }

    @Override
    public Class<PluginInstance> getTClass() {
        return PluginInstance.class;
    }

    @Override
    public PluginInstance findInstance(String mainId, String pluginId) {
        return MapstructUtils.convert(pluginInstanceRepository.findByMainIdAndPluginId(mainId, pluginId), PluginInstance.class);
    }
}
