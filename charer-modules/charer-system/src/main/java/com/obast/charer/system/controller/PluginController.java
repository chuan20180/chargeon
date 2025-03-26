package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.system.dto.bo.PluginInfoBo;
import com.obast.charer.system.dto.vo.PluginInfoVo;
import com.obast.charer.system.service.platform.IPluginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：插件管理
 */
@Api(tags = {"插件管理"})
@Slf4j
@RestController
@RequestMapping("/admin/plugin")
public class PluginController {

    @Autowired
    private IPluginService pluginService;

    @ApiOperation(value = "修改插件")
    @SaCheckPermission("iot:plugin:edit")
    @PostMapping("/edit")
    @Log(title = "插件", businessType = BusinessType.UPDATE)
    public void edit(@Validated(EditGroup.class) @RequestBody Request<PluginInfoBo> request) {
        pluginService.modifyPlugin(request.getData());
    }

    @ApiOperation(value = "插件详情")
    @SaCheckPermission("iot:plugin:list")
    @PostMapping("/detail")
    public PluginInfoVo detail(@RequestBody Request<String> request) {
        return pluginService.getPlugin(request.getData());
    }

    @ApiOperation("获取插件列表")
    @SaCheckPermission("monitor:plugin:list")
    @PostMapping("/list")
    public Paging<PluginInfoVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<PluginInfoBo> query) {
        return pluginService.findPagePluginList(query);
    }

    @ApiOperation("修改插件状态")
    @SaCheckPermission("monitor:plugin:edit")
    @PostMapping("/changeState")
    public void changeState(@RequestBody Request<PluginInfoBo> request) {
        pluginService.changeState(request.getData());
    }

}
