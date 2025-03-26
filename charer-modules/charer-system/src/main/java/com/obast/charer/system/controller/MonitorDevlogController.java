package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.qo.DeviceLogQueryBo;
import com.obast.charer.temporal.IThingModelMessageData;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/monitor/devlog")
@Api(tags = "设备日志记录")
public class MonitorDevlogController extends BaseController {

    @Lazy
    @Autowired
    private IThingModelMessageData thingModelMessageData;

    /**
     * 获取日志记录列表
     */
    @ApiOperation("获取设备日志记录")
    @SaCheckPermission("monitor:devlog:list")
    @PostMapping("/list")
    public Paging<ThingModelMessage> list(@Validated @RequestBody PageRequest<DeviceLogQueryBo> request) {
        return thingModelMessageData.findPage(request);
    }
}
