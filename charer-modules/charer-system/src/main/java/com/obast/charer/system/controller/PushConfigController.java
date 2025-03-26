package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;

import com.obast.charer.system.dto.bo.PushConfigBo;
import com.obast.charer.system.dto.vo.push.PushConfigVo;
import com.obast.charer.system.service.system.IPushConfigManagerService;
import com.obast.charer.qo.PushConfigQueryBo;
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
 * @ Description：短信配置
 */
@Api(tags = {"推送配置"})
@Slf4j
@RestController
@RequestMapping("/admin/pushConfig")
public class PushConfigController {

    @Autowired
    private IPushConfigManagerService pushConfigManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("system:push_config:list")
    @PostMapping("/list")
    public Paging<PushConfigVo> queryPageList(@Validated @RequestBody PageRequest<PushConfigQueryBo> pageRequest) {
        return pushConfigManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "修改属性")
    @SaCheckPermission("system:push_config:changeProperties")
    @Log(title = "推送配置", businessType = BusinessType.UPDATE)
    @PostMapping("/changeProperties")
    public void changeProperties(@RequestBody Request<PushConfigBo> bo) {
        pushConfigManagerService.update(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("system:push_config:edit")
    @Log(title = "推送配置", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<PushConfigBo> bo) {
        PushConfigBo data = bo.getData();
        pushConfigManagerService.updateStatus(data);
    }
}
