package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.system.dto.bo.NotifyConfigBo;
import com.obast.charer.system.dto.vo.notify.NotifyConfigVo;
import com.obast.charer.system.service.system.INotifyConfigManagerService;
import com.obast.charer.qo.NotifyConfigQueryBo;
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
 * @ Description：消息配置
 */
@Api(tags = {"消息配置"})
@Slf4j
@RestController
@RequestMapping("/admin/notifyConfig")
public class NotifyConfigController {

    @Autowired
    private INotifyConfigManagerService notifyConfigManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("system:notify_config:list")
    @PostMapping("/list")
    public Paging<NotifyConfigVo> queryPageList(@Validated @RequestBody PageRequest<NotifyConfigQueryBo> pageRequest) {
        return notifyConfigManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "修改属性")
    @SaCheckPermission("system:notify_config:setting")
    @Log(title = "消息配置", businessType = BusinessType.UPDATE)
    @PostMapping("/changeProperties")
    public void changeProperties(@RequestBody Request<NotifyConfigBo> bo) {
        notifyConfigManagerService.update(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("system:notify_config:change_status")
    @Log(title = "消息配置", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<NotifyConfigBo> bo) {
        NotifyConfigBo data = bo.getData();
        notifyConfigManagerService.updateStatus(data);
    }
}
