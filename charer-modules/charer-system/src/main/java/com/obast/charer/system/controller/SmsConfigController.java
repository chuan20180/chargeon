package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.qo.StationQueryBo;
import com.obast.charer.system.dto.bo.SmsConfigBo;
import com.obast.charer.system.dto.vo.sms.SmsConfigVo;
import com.obast.charer.system.dto.vo.station.StationVo;
import com.obast.charer.system.service.system.ISmsConfigManagerService;
import com.obast.charer.qo.SmsConfigQueryBo;
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

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：短信配置
 */
@Api(tags = {"短信配置"})
@Slf4j
@RestController
@RequestMapping("/admin/smsConfig")
public class SmsConfigController {

    @Autowired
    private ISmsConfigManagerService smsConfigManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("system:sms_config:list")
    @PostMapping("/list")
    public Paging<SmsConfigVo> queryPageList(@Validated @RequestBody PageRequest<SmsConfigQueryBo> pageRequest) {
        return smsConfigManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "参数设置")
    @SaCheckPermission("system:sms_config:setting")
    @Log(title = "短信配置", businessType = BusinessType.UPDATE)
    @PostMapping("/changeProperties")
    public void changeProperties(@RequestBody Request<SmsConfigBo> bo) {
        smsConfigManagerService.update(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("system:sms_config:change_status")
    @Log(title = "短信配置", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SmsConfigBo> bo) {
        SmsConfigBo data = bo.getData();
        smsConfigManagerService.updateStatus(data);
    }



    /**
     * 获取选择框列表
     */
    @SaIgnore
    @ApiOperation(value = "获取选择框列表", notes = "获取选择框列表")
    @PostMapping("/optionSelect")

    public List<SmsConfigVo> optionSelect(@Validated @RequestBody PageRequest<SmsConfigQueryBo> pageRequest) {
        return smsConfigManagerService.queryList(pageRequest);
    }
}
