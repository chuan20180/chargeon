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
import com.obast.charer.qo.SysAppQueryBo;
import com.obast.charer.system.dto.bo.SysAppBo;
import com.obast.charer.system.dto.vo.SysAppVo;
import com.obast.charer.system.service.system.ISysAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 应用信息
 */
@Api(tags = {"应用管理"})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/app")
public class SysAppController extends BaseController {

    private final ISysAppService sysAppService;

    @SaCheckPermission("system:app:list")
    @PostMapping("/list")
    @ApiOperation("查询应用信息列表")
    public Paging<SysAppVo> list(@Validated @RequestBody PageRequest<SysAppQueryBo> pageRequest) {
        return sysAppService.queryPageList(pageRequest);
    }

    @SaCheckPermission("system:app:query")
    @PostMapping("/getDetail")
    @ApiOperation("获取应用信息详细信息")
    public SysAppVo getDetail(@Validated @RequestBody Request<String> request) {
        return sysAppService.queryById(request.getData());
    }

    @SaCheckPermission("system:app:add")
    @Log(title = "应用信息", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    @ApiOperation("新增应用信息")
    public String add(@Validated(AddGroup.class) @RequestBody Request<SysAppBo> request) {
        return sysAppService.insertByBo(request.getData());
    }

    @SaCheckPermission("system:app:edit")
    @Log(title = "应用信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ApiOperation("修改应用信息")
    public boolean edit(@Validated(EditGroup.class) @RequestBody  Request<SysAppBo> request) {
        return sysAppService.updateByBo(request.getData());
    }

    @ApiOperation(value = "设置属性")
    @SaCheckPermission("system:app:config")
    @Log(title = "应用信息", businessType = BusinessType.UPDATE)
    @PostMapping("/changeConfig")
    public void changeConfig(@RequestBody Request<SysAppBo> bo) {
        SysAppBo data = bo.getData();
        sysAppService.updateConfig(data);
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("system:app:edit")
    @Log(title = "应用信息", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SysAppBo> bo) {
        sysAppService.updateStatus(bo.getData());
    }

    @SaCheckPermission("system:app:delete")
    @Log(title = "应用信息", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    @ApiOperation("删除应用信息")
    public boolean remove(@Validated @RequestBody Request<String> query) {
        return sysAppService.deleteById(query.getData());
    }
}
