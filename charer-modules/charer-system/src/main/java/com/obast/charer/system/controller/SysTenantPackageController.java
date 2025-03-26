package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.qo.SysTenantPackageQueryBo;
import com.obast.charer.system.dto.bo.SysTenantPackageBo;
import com.obast.charer.system.dto.vo.tenant.SysTenantPackageVo;
import com.obast.charer.system.service.platform.ISysTenantPackageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 租户套餐管理
 */

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/tenant/package")
public class SysTenantPackageController extends BaseController {

    @Autowired
    private ISysTenantPackageService tenantPackageService;

    @ApiOperation("分页列表")
    @SaCheckPermission("platform:tenant_package:list")
    @PostMapping("/list")
    public Paging<SysTenantPackageVo> queryPageList(@Validated @RequestBody PageRequest<SysTenantPackageQueryBo> pageRequest) {
        return tenantPackageService.queryPageList(pageRequest);
    }

    @ApiOperation("详细信息")
    @SaCheckPermission("platform:tenant_package:query")
    @PostMapping("/getInfo")
    public SysTenantPackageVo getInfo(@RequestBody @Validated Request<String> bo) {
        return tenantPackageService.queryDetail(bo.getData());
    }

    @ApiOperation("新增")
    @SaCheckPermission("platform:tenant_package:add")
    @Log(title = "租户套餐", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public void add(@Validated(AddGroup.class) @RequestBody Request<SysTenantPackageBo> bo) {
        tenantPackageService.add(bo.getData());
    }

    @ApiOperation("修改")
    @SaCheckPermission("platform:tenant_package:edit")
    @Log(title = "租户套餐", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public void edit(@Validated(EditGroup.class) @RequestBody Request<SysTenantPackageBo> bo) {
        tenantPackageService.update(bo.getData());
    }

    @ApiOperation("状态修改")
    @SaCheckPermission("platform:tenant_package:edit")
    @Log(title = "租户套餐", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SysTenantPackageBo> bo) {
        tenantPackageService.updateStatus(bo.getData());
    }

    @ApiOperation("删除")
    @SaCheckPermission("platform:tenant_package:delete")
    @Log(title = "租户套餐", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return tenantPackageService.delete(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("platform:tenant_package:delete")
    @Log(title = "租户套餐", businessType = BusinessType.DELETE)
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return tenantPackageService.batchDelete(request.getData());
    }

    @ApiOperation("下拉选列表")
    @PostMapping("/optionSelect")
    public List<SysTenantPackageVo> optionSelect() {
        SysTenantPackageQueryBo bo = new SysTenantPackageQueryBo();
        bo.setStatus(EnableStatusEnum.Enabled);
        return tenantPackageService.queryList(bo);
    }
}
