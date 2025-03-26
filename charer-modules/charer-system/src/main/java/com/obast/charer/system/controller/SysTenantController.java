package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.excel.utils.ExcelUtil;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.tenant.helper.TenantHelper;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.web.core.BaseController;

import com.obast.charer.system.dto.bo.SysTenantBo;
import com.obast.charer.system.dto.bo.SysTenantSyncBo;
import com.obast.charer.system.dto.vo.tenant.SysTenantVo;
import com.obast.charer.system.service.platform.ISysTenantService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.obast.charer.qo.SysTenantQueryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 租户管理
 *
 * @author Michelle.Chung
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/tenant")
@Api(tags = "租户管理")
public class SysTenantController extends BaseController {

    private final ISysTenantService tenantService;

    /**
     * 查询租户列表
     */
    @ApiOperation("查询租户列表")
    @SaCheckPermission("platform:tenant:list")
    @PostMapping("/list")
    public Paging<SysTenantVo> list(@Validated @RequestBody PageRequest<SysTenantQueryBo> query) {
        return tenantService.queryPageList(query);
    }

    /**
     * 导出租户列表
     */
    @ApiOperation("导出租户列表")
    @SaCheckPermission("platform:tenant:export")
    @Log(title = "租户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysTenantQueryBo bo, HttpServletResponse response) {
        List<SysTenantVo> list = tenantService.queryList(bo);
        ExcelUtil.exportExcel(list, "租户", SysTenantVo.class, response);
    }

    /**
     * 获取租户详细信息
     */
    @ApiOperation("获取租户详细信息")
    @SaCheckPermission("platform:tenant:query")
    @PostMapping("/getDetail")
    public SysTenantVo getInfo(@Validated @RequestBody Request<String> bo) {
        return tenantService.queryById(bo.getData());
    }

    /**
     * 新增租户
     */
    @ApiOperation("新增租户")
    @SaCheckPermission("platform:tenant:add")
    @Log(title = "租户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public void add(@Validated(AddGroup.class) @RequestBody Request<SysTenantBo> bo) {
        SysTenantBo data = bo.getData();
        TenantHelper.ignore(()->tenantService.insertByBo(data));
    }

    /**
     * 修改租户
     */
    @ApiOperation("修改租户")
    @SaCheckPermission("platform:tenant:edit")
    @Log(title = "租户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public void edit(@Validated(EditGroup.class) @RequestBody Request<SysTenantBo> bo) {
        SysTenantBo data = bo.getData();
        TenantHelper.ignore(()->tenantService.updateByBo(data));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("platform:tenant:edit")
    @Log(title = "租户", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SysTenantBo> bo) {
        SysTenantBo data = bo.getData();
        tenantService.checkTenantAllowed(data.getTenantId());
        tenantService.updateStatus(data);
    }

    /**
     * 删除租户
     */
    @ApiOperation("删除租户")
    @SaCheckPermission("platform:tenant:delete")
    @Log(title = "租户", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public void remove(@RequestBody Request<SysTenantBo> bo) {
        SysTenantBo data = bo.getData();
        tenantService.checkTenantAllowed(data.getTenantId());
        TenantHelper.ignore(() -> tenantService.deleteById(data.getId()));
    }

    /**
     * 动态切换租户
     */
    @ApiOperation("动态切换租户")
    @PostMapping("/dynamic")
    public void dynamicTenant(@Validated @RequestBody Request<String> bo) {
        TenantHelper.setDynamic(bo.getData());
    }

    /**
     * 清除动态租户
     */
    @ApiOperation("清除动态租户")
    @PostMapping("/dynamic/clear")
    public void dynamicClear() {
        TenantHelper.clearDynamic();
    }

    /**
     * 同步租户套餐
     */
    @ApiOperation("同步租户套餐")
    @SaCheckPermission("platform:tenant:edit")
    @Log(title = "租户", businessType = BusinessType.UPDATE)
    @PostMapping("/syncTenantPackage")
    public void syncTenantPackage(@RequestBody Request<SysTenantSyncBo> bo) {
        SysTenantSyncBo data = bo.getData();
        tenantService.checkTenantAllowed(data.getTenantId());
        TenantHelper.ignore(() -> tenantService.syncTenantPackage(data.getTenantId(), data.getPackageId()));
    }

    /**
     * 同步租户套餐
     */
    @ApiOperation("重置管理员密码")
    @SaCheckPermission("platform:tenant:edit")
    @Log(title = "重置管理员密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetAdminPwd")
    public void resetAdminPwd(@RequestBody Request<SysTenantBo> bo) {
        SysTenantBo data = bo.getData();
        tenantService.checkTenantAllowed(data.getTenantId());
        TenantHelper.ignore(() -> tenantService.resetAdminPwd(data.getTenantId(), data.getPassword()));
    }

}
