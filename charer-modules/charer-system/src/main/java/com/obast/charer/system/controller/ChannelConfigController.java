package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.system.dto.bo.ChannelConfigBo;
import com.obast.charer.system.dto.vo.notify.ChannelConfigVo;
import com.obast.charer.system.service.system.IChannelConfigManagerService;
import com.obast.charer.qo.ChannelConfigQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
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
 * @ Description：通道配置
 */
@Api(tags = {"通道配置"})
@Slf4j
@RestController
@RequestMapping("/admin/channelConfig")
public class ChannelConfigController {

    @Autowired
    private IChannelConfigManagerService channelConfigManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("system:channel_config:list")
    @PostMapping("/list")
    public Paging<ChannelConfigVo> queryPageList(@Validated @RequestBody PageRequest<ChannelConfigQueryBo> pageRequest) {
        return channelConfigManagerService.queryPageList(pageRequest);
    }


    @ApiOperation(value = "修改")
    @SaCheckPermission("system:channel_config:edit")
    @PostMapping("/edit")
    public boolean edit(@Validated @RequestBody Request<ChannelConfigBo> bo) {
        return channelConfigManagerService.update(bo.getData());
    }

    @ApiOperation(value = "修改属性")
    @SaCheckPermission("system:channel_config:changeProperties")
    @Log(title = "消息配置", businessType = BusinessType.UPDATE)
    @PostMapping("/changeProperties")
    public void changeProperties(@RequestBody Request<ChannelConfigBo> bo) {
        channelConfigManagerService.update(bo.getData());
    }
}
