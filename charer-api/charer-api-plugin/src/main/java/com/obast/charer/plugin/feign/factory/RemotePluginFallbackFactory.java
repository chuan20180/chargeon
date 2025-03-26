package com.obast.charer.plugin.feign.factory;

import com.obast.charer.common.api.Response;
import com.obast.charer.model.price.Price;
import com.obast.charer.plugin.feign.IRemoteChargerService;
import com.obast.charer.plugin.feign.IRemotePluginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充电柱控制服务
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemotePluginFallbackFactory implements FallbackFactory<IRemotePluginService> {

    @Override
    public IRemotePluginService create(Throwable throwable) {
        log.error("插件服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new IRemotePluginService() {

            @Override
            public Response<?> start(String pluginId) {
                return Response.fail("插件启动失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> stop(String  pluginId) {
                return Response.fail("插件停止失败:" + throwable.getMessage());
            }

        };
    }
}
