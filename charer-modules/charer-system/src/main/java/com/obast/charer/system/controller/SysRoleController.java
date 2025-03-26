package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.bo.SysRoleAuthBo;
import com.obast.charer.system.dto.bo.SysRoleBo;
import com.obast.charer.system.dto.bo.SysUserBo;
import com.obast.charer.system.dto.vo.DeptTreeSelectVo;
import com.obast.charer.system.dto.vo.system.SysRoleVo;
import com.obast.charer.system.dto.vo.SysUserVo;
import com.obast.charer.system.service.system.ISysDeptService;
import com.obast.charer.system.service.system.ISysRoleManageService;
import com.obast.charer.system.service.system.ISysUserManageService;
import com.obast.charer.model.system.SysUserRole;
import com.obast.charer.qo.SysDeptQueryBo;
import com.obast.charer.qo.SysRoleQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色信息
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/role")
public class SysRoleController extends BaseController {

    private final ISysRoleManageService sysRoleManageService;


    /**
     * 获取角色信息列表
     */
    @ApiOperation(value = "获取角色信息列表", notes = "获取角色信息列表,根据查询条件分页")
    @SaCheckPermission("system:role:list")
    @PostMapping("/list")
    public Paging<SysRoleVo> list(@RequestBody @Validated PageRequest<SysRoleQueryBo> pageRequest) {
        return sysRoleManageService.queryPageList(pageRequest);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @ApiOperation(value = "根据角色编号获取详细信息", notes = "根据角色编号获取详细信息")
    @SaCheckPermission("system:role:query")
    @PostMapping(value = "/getInfo")
    public SysRoleVo getInfo(@Validated @RequestBody Request<String> bo) {
        String roleId = bo.getData();
        sysRoleManageService.checkRoleDataScope(roleId);
        return sysRoleManageService.selectRoleById(roleId);
    }

    /**
     * 新增角色
     */
    @ApiOperation(value = "新增角色", notes = "新增角色")
    @SaCheckPermission("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public void add(@Validated @RequestBody Request<SysRoleBo> bo) {
        SysRoleBo role = bo.getData();
        if (!sysRoleManageService.checkRoleNameUnique(role)) {
            fail("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!sysRoleManageService.checkRoleKeyUnique(role)) {
            fail("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        sysRoleManageService.insertRole(role);

    }

    /**
     * 修改保存角色
     */
    @ApiOperation(value = "修改保存角色", notes = "修改保存角色")
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public void edit(@Validated @RequestBody Request<SysRoleBo> bo) {
        SysRoleBo role = bo.getData();

        sysRoleManageService.checkRoleAllowed(role.getId());
        sysRoleManageService.checkRoleDataScope(role.getId());
        if (!sysRoleManageService.checkRoleNameUnique(role)) {
            fail("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!sysRoleManageService.checkRoleKeyUnique(role)) {
            fail("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }

        if (sysRoleManageService.updateRole(role) > 0) {
            sysRoleManageService.cleanOnlineUserByRole(role.getId());
            return;
        }
        fail("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 状态修改
     */
    @ApiOperation(value = "状态修改", notes = "状态修改")
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SysRoleBo> bo) {
        SysRoleBo role = bo.getData();
        sysRoleManageService.checkRoleAllowed(role.getId());
        sysRoleManageService.checkRoleDataScope(role.getId());
        sysRoleManageService.updateStatus(role.getId(), role.getStatus());
    }

    /**
     * 删除角色
     */
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @SaCheckPermission("system:role:delete")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public void remove(@Validated @RequestBody Request<List<String>> bo) {
        sysRoleManageService.deleteRoleByIds(bo.getData());
    }

    /**
     * 获取角色选择框列表
     */
    @ApiOperation(value = "获取角色选择框列表", notes = "获取角色选择框列表")
    @PostMapping("/optionselect")
    public List<SysRoleVo> optionselect() {
        return sysRoleManageService.selectRoleAll();
    }



}
