package com.obast.charer.plugin.init;


import com.gitee.starblues.integration.operator.PluginOperator;
import com.obast.charer.data.business.IPluginInfoData;
import com.obast.charer.model.plugin.PluginInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author sjg
 */
@Slf4j
@Service
public class PluginInit {

    @Autowired
    private IPluginInfoData pluginInfoData;

    @Autowired
    private PluginOperator pluginOperator;


    @PostConstruct
    public void init() {
        Executors.newSingleThreadScheduledExecutor().schedule(this::startPlugins, 5, TimeUnit.SECONDS);
    }

    @SneakyThrows
    private void startPlugins() {
        while (!pluginOperator.inited()) {
            Thread.sleep(1000L);
        }

        for (PluginInfo pluginInfo : pluginInfoData.findAll()) {
            if (!PluginInfo.STATE_RUNNING.equals(pluginInfo.getState())) {
                continue;
            }
            try {
                com.gitee.starblues.core.PluginInfo plugin = pluginOperator.getPluginInfo(pluginInfo.getPluginId());
                if (plugin != null) {
                    pluginOperator.start(plugin.getPluginId());
                }
            } catch (Exception e) {
                log.error("start plugin error", e);
            }
        }
    }
}
