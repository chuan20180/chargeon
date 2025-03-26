package com.obast.charer.plugin.feign;

import com.obast.charer.common.api.Response;
import com.obast.charer.common.constant.SecurityConstants;
import com.obast.charer.common.constant.ServiceConstants;
import com.obast.charer.plugin.feign.factory.RemotePluginFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author xueyi
 */
@FeignClient(
        contextId = "remotePluginService",
        path = "/inner/plugin",
        value = ServiceConstants.PLUGIN_SERVICE,
        fallbackFactory = RemotePluginFallbackFactory.class
)
public interface IRemotePluginService {

    @GetMapping(value = "/start", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> start(@RequestParam("pluginId") String pluginId);

    @GetMapping(value = "/stop", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> stop(@RequestParam("pluginId") String pluginId);


}
