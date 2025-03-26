package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.qo.SysOssConfigQueryBo;
import com.obast.charer.system.dto.bo.SysOssConfigBo;
import com.obast.charer.system.dto.vo.SysOssConfigVo;
import com.obast.charer.system.service.system.ISysOssConfigService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 对象存储配置
 */
@Validated
@RequiredArgsConstructor
@RestController
@Controller
@ResponseBody
@RequestMapping("/admin/resource/oss/config")
public class SysOssConfigController extends BaseController {

    private final ISysOssConfigService ossConfigService;

    @ApiOperation(value = "查询对象存储配置列表", notes = "查询对象存储配置列表")
    @SaCheckPermission("platform:oss_config:list")
    @PostMapping("/list")
    public Paging<SysOssConfigVo> list(@Validated @RequestBody PageRequest<SysOssConfigQueryBo> query) {
        return ossConfigService.queryPageList(query);
    }

    @ApiOperation(value = "获取对象存储配置详细信息", notes = "获取对象存储配置详细信息")
    @SaCheckPermission("platform:oss_config:query")
    @PostMapping("/detail")
    public SysOssConfigVo getInfo(@Validated @RequestBody Request<String> bo) {
        return ossConfigService.queryById(bo.getData());
    }

    @ApiOperation(value = "新增对象存储配置", notes = "新增对象存储配置")
    @SaCheckPermission("platform:oss_config:add")
    @Log(title = "对象存储配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public void add(@Validated(AddGroup.class) @RequestBody Request<SysOssConfigBo> bo) {
        ossConfigService.insertByBo(bo.getData());
    }

    @ApiOperation(value = "修改对象存储配置", notes = "修改对象存储配置")
    @SaCheckPermission("platform:oss_config:edit")
    @Log(title = "对象存储配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public void edit(@Validated(EditGroup.class) @RequestBody Request<SysOssConfigBo> bo) {
        ossConfigService.updateByBo(bo.getData());
    }

    @ApiOperation(value = "状态修改", notes = "状态修改")
    @SaCheckPermission("platform:oss_config:edit")
    @Log(title = "对象存储状态修改", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SysOssConfigBo> bo) {
        ossConfigService.updateOssConfigStatus(bo.getData());
    }

    @ApiOperation(value = "删除对象存储配置", notes = "删除对象存储配置")
    @SaCheckPermission("platform:oss_config:delete")
    @Log(title = "对象存储配置", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public void remove(@Validated @RequestBody Request<String> bo) {
        ossConfigService.delete(bo.getData());
    }

}
