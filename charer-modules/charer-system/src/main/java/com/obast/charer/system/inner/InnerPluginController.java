package com.obast.charer.system.inner;

import com.obast.charer.common.api.Response;
import com.obast.charer.system.service.platform.IPluginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：内部插件信息
 */
@Slf4j
@Api(tags = {"内部插件信息"})
@RestController
@RequestMapping("/inner/plugin")
public class InnerPluginController {

    @Autowired
    private IPluginService pluginService;

    @ApiOperation("获取插件信息")
    @PostMapping("/info")
    public Response<?> info(String pluginId) {
        return Response.success(pluginService.getPlugin(pluginId));
    }
}
