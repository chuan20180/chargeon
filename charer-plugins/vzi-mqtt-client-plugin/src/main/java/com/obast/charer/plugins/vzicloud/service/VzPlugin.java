package com.obast.charer.plugins.vzicloud.service;


import com.obast.charer.common.plugin.core.IPlugin;
import com.obast.charer.common.plugin.core.IPluginConfig;
import com.obast.charer.plugins.vzicloud.config.VziConfig;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.gitee.starblues.bootstrap.annotation.AutowiredType;
import com.gitee.starblues.bootstrap.realize.PluginCloseListener;
import com.gitee.starblues.core.PluginCloseType;
import com.gitee.starblues.core.PluginInfo;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * tcp插件
 *
 * @author sjg
 */
@Slf4j
@Service
public class VzPlugin implements PluginCloseListener, IPlugin {

    @Autowired
    private PluginInfo pluginInfo;


    @Autowired
    private VzVerticle vzVerticle;

    @Autowired
    private VziConfig vziConfig;

    @Autowired
    @AutowiredType(AutowiredType.Type.MAIN_PLUGIN)
    private IPluginConfig pluginConfig;

    private Vertx vertx;
    private String deployedId;

    @PostConstruct
    public void init() {
        vertx = Vertx.vertx();
        try {
            //获取插件最新配置替换当前配置
            Map<String, Object> config = pluginConfig.getConfig(pluginInfo.getPluginId());
            BeanUtil.copyProperties(config, vziConfig, CopyOptions.create().ignoreNullValue());


            log.info("插件信息: {}", vziConfig);

            vzVerticle.setConfig(vziConfig);

            Future<String> future = vertx.deployVerticle(vzVerticle);
            future.onSuccess((s -> {
                deployedId = s;
                log.info("vz plugin started success");
            }));
            future.onFailure(Throwable::printStackTrace);
        } catch (Throwable e) {
            log.error("init vz plugin error", e);
        }
    }

    @SneakyThrows
    @Override
    public void close(GenericApplicationContext applicationContext, PluginInfo pluginInfo, PluginCloseType closeType) {
        log.info("plugin close,type:{},pluginId:{}", closeType, pluginInfo.getPluginId());
        try {
            if (deployedId != null) {
                CountDownLatch wait = new CountDownLatch(1);
                Future<Void> future = vertx.undeploy(deployedId);
                future.onSuccess(unused -> {
                    log.info("vz plugin stopped success");
                    wait.countDown();
                });
                future.onFailure(h -> {
                    log.info("vz plugin stopped failed");
                    h.printStackTrace();
                    wait.countDown();
                });
                wait.await(5, TimeUnit.SECONDS);
            }
        } catch (Throwable e) {
            log.error("close plugin error", e);
        }
    }

    @Override
    public Map<String, Object> getLinkInfo(String pk, String dn) {
        return null;
    }
}
