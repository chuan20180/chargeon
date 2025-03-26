package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.system.dto.bo.ChannelConfigBo;
import com.obast.charer.system.dto.vo.notify.ChannelConfigVo;
import com.obast.charer.system.service.platform.NotifyService;
import com.obast.charer.model.notify.ChannelConfig;
import com.obast.charer.model.notify.NotifyMessage;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：消息通知管理
 */
@Api(tags = {"消息通知"})
@Slf4j
@RestController
@RequestMapping("/admin/notify")
public class NotifyController {

    @Resource
    private NotifyService notifyService;

//    @ApiOperation("获取通道类型列表")
//    @SaCheckPermission("iot:channel:query")
//    @PostMapping("/channel/getList")
//    public List<Channel> getChannelList() {
//        return notifyService.getChannelList();
//    }

    @ApiOperation("获取通道配置分页列表")
    @SaCheckPermission("iot:channel:query")
    @PostMapping("/channel/config/getList")
    public Paging<ChannelConfigVo> getChannelConfigList(@RequestBody @Validated(QueryGroup.class) PageRequest<ChannelConfigBo> request) {
        return notifyService.getChannelConfigList(request);
    }

    @ApiOperation("获取通道配置列表")
    @SaCheckPermission("iot:channel:query")
    @PostMapping("/channel/config/getAll")
    public List<ChannelConfigVo> getChannelConfigAll() {
        return notifyService.getChannelConfigAll();
    }

    @ApiOperation("新增通道配置")
    @SaCheckPermission("iot:channel:add")
    @PostMapping("/channel/config/add")
    public ChannelConfig addChannelConfig(@RequestBody @Validated(EditGroup.class) Request<ChannelConfig> request) {
        return notifyService.addChannelConfig(request.getData());
    }

    @ApiOperation("根据ID获取通道配置")
    @SaCheckPermission("iot:channel:query")
    @PostMapping("/channel/config/getById")
    public ChannelConfig getChannelConfigById(@RequestBody @Validated(QueryGroup.class) Request<String> request) {
        return notifyService.getChannelConfigById(request.getData());
    }

    @ApiOperation("修改通道配置")
    @SaCheckPermission("iot:channel:edit")
    @PostMapping("/channel/config/updateById")
    public ChannelConfig updateChannelConfigById(@RequestBody @Validated Request<ChannelConfig> request) {
        return notifyService.updateChannelConfigById(request.getData());
    }

    @ApiOperation("删除通道配置")
    @SaCheckPermission("iot:channel:remove")
    @PostMapping("/channel/config/delById")
    public Boolean delChannelConfigById(@RequestBody @Validated Request<String> request) {
        return notifyService.delChannelConfigById(request.getData());
    }


    @ApiOperation("消息列表")
    @SaCheckPermission("iot:channelMsg:query")
    @PostMapping("/message/getList")
    public Paging<NotifyMessage> messageList(@RequestBody @Validated(QueryGroup.class) PageRequest<NotifyMessage> request) {
        return notifyService.getNotifyMessageList(request);
    }

}
