package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.excel.utils.ExcelUtil;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.tenant.helper.TenantHelper;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StreamUtils;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.model.system.SysUser;
import com.obast.charer.qo.SysDeptQueryBo;
import com.obast.charer.qo.SysUserQueryBo;
import com.obast.charer.system.dto.bo.SysUserBo;
import com.obast.charer.system.dto.bo.SysUserRolesBo;
import com.obast.charer.system.dto.vo.*;
import com.obast.charer.system.dto.vo.system.SysRoleVo;
import com.obast.charer.system.service.platform.ISysTenantService;
import com.obast.charer.system.service.system.ISysDeptService;
import com.obast.charer.system.service.system.ISysPostService;
import com.obast.charer.system.service.system.ISysRoleManageService;
import com.obast.charer.system.service.system.ISysUserManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 用户信息
 *
 * @author Lion Li
 */
@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/user")
@Api(tags = "用户信息")
public class SysUserController extends BaseController {

    private final ISysUserManageService sysUserManageService;

    private final ISysRoleManageService sysRoleManageService;
    private final ISysPostService postService;
    private final ISysDeptService deptService;
    private final ISysTenantService tenantService;


    @ApiOperation("获取用户列表")
    @SaCheckPermission("system:user:list")
    @PostMapping("/list")
    public Paging<SysUserVo> list(@RequestBody PageRequest<SysUserQueryBo> pageRequest) {
        return sysUserManageService.queryPageList(pageRequest);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation("获取用户信息")
    @PostMapping("/getInfo")
    public UserInfoVo getInfo() {
        UserInfoVo userInfoVo = new UserInfoVo();
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (LoginHelper.isSuperAdmin()) {
            // 超级管理员 如果重新加载用户信息需清除动态租户
            TenantHelper.clearDynamic();
        }
        SysUserVo user = sysUserManageService.selectUserById(loginUser.getUserId());
        userInfoVo.setUser(user);



        userInfoVo.setPermissions(loginUser.getMenuPermission());
        userInfoVo.setRoles(loginUser.getRolePermission());
        userInfoVo.setTenant(tenantService.queryById(loginUser.getTenantId()));
        return userInfoVo;
    }

    /**
     * 根据用户编号获取详细信息
     * 用户ID
     */
    @ApiOperation("根据用户编号获取详细信息")
    @SaCheckPermission("system:user:query")
    @PostMapping(value = {"/getDetail"})
    public SysUserInfoVo getInfo(@Validated @RequestBody Request<String> req) {
        String userId = req.getData();
        sysUserManageService.checkUserDataScope(userId);
        SysUserInfoVo userInfoVo = new SysUserInfoVo();
        List<SysRoleVo> roles = sysRoleManageService.selectRoleAll();
        userInfoVo.setRoles(LoginHelper.isSuperAdmin(userId) ? roles : StreamUtils.filter(roles, r -> !r.isSuperAdmin()));
        userInfoVo.setPosts(postService.selectPostAll());
        if (ObjectUtil.isNotNull(userId)) {
            SysUserVo sysUser = sysUserManageService.selectUserById(userId);
            userInfoVo.setUser(sysUser);
            userInfoVo.setRoleIds(StreamUtils.toList(sysUser.getRoles(), SysRoleVo::getId));
            userInfoVo.setPostIds(postService.selectPostListByUserId(userId));
        }
        return userInfoVo;
    }

    /**
     * 新增用户
     */
    @ApiOperation("新增用户")
    @SaCheckPermission("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public void add(@Validated(AddGroup.class) @RequestBody Request<SysUserBo> reqUser) {
        SysUserBo user = reqUser.getData();
        if (!sysUserManageService.checkUserNameUnique(user.to(SysUser.class))) {
            fail("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        if (!tenantService.checkAccountBalance(TenantHelper.getTenantId())) {
            fail("当前租户下用户名额不足，请联系管理员");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword()));

        sysUserManageService.insertUser(user);
    }

    /**
     * 修改用户
     */
    @ApiOperation("修改用户")
    @SaCheckPermission("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public void edit(@Validated(EditGroup.class) @RequestBody Request<SysUserBo> reqUser) {
        SysUserBo user = reqUser.getData();
        sysUserManageService.checkUserAllowed(user);
        sysUserManageService.checkUserDataScope(user.getId());
        if (!sysUserManageService.checkUserNameUnique(user.to(SysUser.class))) {
            fail("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        sysUserManageService.updateUser(user);
    }

    /**
     * 删除用户
     */
    @ApiOperation("删除用户")
    @SaCheckPermission("system:user:delete")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public void remove(@Validated @RequestBody Request<String> bo) {
        String userId = bo.getData();
        if (userId.equals(LoginHelper.getUserId())) {
            fail("当前用户不能删除");
        }
        sysUserManageService.deleteUserById(userId);
    }

    /**
     * 重置密码
     */
    @ApiOperation("重置密码")
    @SaCheckPermission("system:user:resetPwd")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    public void resetPwd(@RequestBody @Validated(EditGroup.class) Request<SysUserBo> reqUser) {
        SysUserBo user = reqUser.getData();
        sysUserManageService.checkUserAllowed(user);
        sysUserManageService.checkUserDataScope(user.getId());
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        sysUserManageService.resetUserPwd(user.getId(), user.getPassword());
    }

    /**
     * 状态修改
     */
    @ApiOperation("状态修改")
    @SaCheckPermission("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody @Validated(EditGroup.class) Request<SysUserBo> reqUser) {
        SysUserBo user = reqUser.getData();
        sysUserManageService.checkUserAllowed(user);
        sysUserManageService.checkUserDataScope(user.getId());
        sysUserManageService.updateUserStatus(user.getId(), user.getStatus());
    }

    /**
     * 根据用户编号获取授权角色
     */
    @ApiOperation("根据用户编号获取授权角色")
    @SaCheckPermission("system:user:query")
    @PostMapping("/authRoleByUserId")
    public SysUserInfoVo authRole(@Validated @RequestBody Request<String> bo) {
        String userId = bo.getData();
        SysUserVo user = sysUserManageService.selectUserById(userId);
        List<SysRoleVo> roles = sysRoleManageService.selectRolesByUserId(userId);
        SysUserInfoVo userInfoVo = new SysUserInfoVo();
        userInfoVo.setUser(user);
        userInfoVo.setRoles(LoginHelper.isSuperAdmin(userId) ? roles : StreamUtils.filter(roles, r -> !r.isSuperAdmin()));
        return userInfoVo;
    }

    /**
     * 用户授权角色
     *
     * @param userRole 用户角色
     */
    @ApiOperation("用户授权角色")
    @SaCheckPermission("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PostMapping("/authRole")
    public void insertAuthRole(@RequestBody @Validated Request<SysUserRolesBo> userRole) {
        SysUserRolesBo data = userRole.getData();
        String userId = data.getUserId();
        sysUserManageService.checkUserDataScope(userId);
        sysUserManageService.insertUserAuth(userId, data.getRoleIds());
    }

    /**
     * 获取部门树列表
     */
    @ApiOperation("获取部门树列表")
    @SaCheckPermission("system:user:list")
    @PostMapping("/deptTree")
    public List<Tree<String>> deptTree(@RequestBody @Validated(QueryGroup.class) Request<SysDeptQueryBo> reqDept) {
        return deptService.selectDeptTreeList(reqDept.getData());
    }

    @ApiOperation("导出用户列表")
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:user:export")
    @PostMapping("/export")
    public void export(@RequestBody PageRequest<SysUserQueryBo> pageRequest, HttpServletResponse response) {
        List<SysUserVo> list = sysUserManageService.queryList(pageRequest);
        List<SysUserExportVo> listVo = MapstructUtils.convert(list, SysUserExportVo.class);
        ExcelUtil.exportExcel(listVo, "用户列表", SysUserExportVo.class, response);
    }

}
