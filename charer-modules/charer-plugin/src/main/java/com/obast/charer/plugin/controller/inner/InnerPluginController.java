package com.obast.charer.plugin.controller.inner;

import com.gitee.starblues.integration.operator.PluginOperator;
import com.obast.charer.common.api.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电订单管理服务实现
 */
@Slf4j
@Api(tags = {"内部插件控制"})
@RestController
@RequestMapping("/inner/plugin")
public class InnerPluginController {

    @Autowired
    private PluginOperator pluginOperator;

    @ApiOperation("启动")
    @GetMapping("/start")
    Response<?> startCharge(@RequestParam("pluginId") String pluginId) {
        pluginOperator.start(pluginId);
        return Response.success();
    }

    @ApiOperation("停止")
    @GetMapping("/stop")
    Response<?> stop(@RequestParam("pluginId") String pluginId) {
        pluginOperator.stop(pluginId);
        return Response.success();
    }
}
