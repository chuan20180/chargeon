package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.constant.UserConstants;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.bo.SysMenuBo;
import com.obast.charer.system.dto.vo.MenuTreeSelectVo;
import com.obast.charer.system.dto.vo.RouterVo;
import com.obast.charer.system.dto.vo.SysMenuVo;
import com.obast.charer.system.service.system.ISysMenuManageService;

import com.obast.charer.model.system.SysMenu;
import com.obast.charer.qo.SysMenuQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.lang.tree.Tree;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private ISysMenuManageService sysMenuManageService;


    @ApiOperation("获取路由信息")
    @PostMapping("/getRouters")
    public List<RouterVo> getRouters() {
        List<SysMenu> menus = sysMenuManageService.selectMenuTreeByUserId(LoginHelper.getUserId());
        return sysMenuManageService.buildMenus(menus);
    }

    @ApiOperation("获取菜单列表")
    @SaCheckPermission("system:menu:list")
    @PostMapping("/list")
    public List<SysMenuVo> list(@Validated @RequestBody PageRequest<SysMenuQueryBo> pageRequest) {
        return sysMenuManageService.queryAllList(pageRequest.getData());
    }

    /**
     * 根据菜单编号获取详细信息
     *
     * @param menuId 菜单ID
     */
    @SaCheckPermission("system:menu:query")
    @PostMapping(value = "/getInfo")
    public SysMenuVo getInfo(@RequestBody Request<String> menuId) {
        return sysMenuManageService.selectMenuById(menuId.getData());
    }

    @ApiOperation("获取平台角色可用菜单下拉树列表")
    @PostMapping("/platformTreeSelect")
    public List<Tree<String>> platformTreeSelect(@Validated @RequestBody  PageRequest<SysMenuQueryBo> pageRequest) {
        List<SysMenuVo> menus = sysMenuManageService.queryPlatformList(pageRequest.getData());
        return sysMenuManageService.buildMenuTreeSelect(menus);
    }

    @ApiOperation("获取租户可用菜单下拉树列表")
    @SaCheckRole(value = { TenantConstants.SUPER_ADMIN_ROLE_KEY })
    @PostMapping("/tenantTreeSelect")
    public List<Tree<String>> tenantTreeSelect(@Validated @RequestBody  PageRequest<SysMenuQueryBo> pageRequest) {
        List<SysMenuVo> menus = sysMenuManageService.queryTenantList(pageRequest.getData());
        return sysMenuManageService.buildMenuTreeSelect(menus);
    }

    @ApiOperation("获取代理可用菜单下拉树列表")
    @PostMapping("/agentTreeSelect")
    public List<Tree<String>> agentTreeSelect(@Validated @RequestBody  PageRequest<SysMenuQueryBo> pageRequest) {
        List<SysMenuVo> menus = sysMenuManageService.queryAgentList(pageRequest.getData());
        return sysMenuManageService.buildMenuTreeSelect(menus);
    }

    @ApiOperation("获取合作商可用菜单下拉树列表")
    @PostMapping("/dealerTreeSelect")
    public List<Tree<String>> dealerTreeSelect(@Validated @RequestBody  PageRequest<SysMenuQueryBo> pageRequest) {
        List<SysMenuVo> menus = sysMenuManageService.queryDealerList(pageRequest.getData());
        return sysMenuManageService.buildMenuTreeSelect(menus);
    }

    /**
     * 加载对应角色菜单列表树
     *
     */
    @ApiOperation("加载对应角色菜单列表树")
    @SaCheckRole(value = { TenantConstants.SUPER_ADMIN_ROLE_KEY, TenantConstants.TENANT_ADMIN_ROLE_KEY }, mode = SaMode.OR)
    @PostMapping(value = "/roleMenuTreeselectByRoleId")
    public MenuTreeSelectVo roleMenuTreeSelect(@Validated @RequestBody Request<String> bo) {
        List<SysMenuVo> menus = sysMenuManageService.queryAllList(new SysMenuQueryBo());
        MenuTreeSelectVo selectVo = new MenuTreeSelectVo();
        selectVo.setCheckedKeys(sysMenuManageService.selectMenuListByRoleId(bo.getData()));
        selectVo.setMenus(sysMenuManageService.buildMenuTreeSelect(menus));
        return selectVo;
    }

    /**
     * 新增菜单
     */
    @ApiOperation("新增菜单")
    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:add")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public void add(@Validated @RequestBody Request<SysMenuBo> bo) {
        SysMenuBo menu = bo.getData();
        if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            fail("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        sysMenuManageService.insertMenu(menu);
    }

    /**
     * 修改菜单
     */
    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:edit")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public void edit(@Validated @RequestBody Request<SysMenuBo> bo) {
        SysMenuBo menu = bo.getData();
        if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            fail("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getId().equals(menu.getParentId())) {
            fail("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        sysMenuManageService.updateMenu(menu);
    }

    /**
     * 删除菜单
     *
     */
    @ApiOperation("删除菜单")
    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public void remove(@Validated @RequestBody Request<String> bo) {
        String menuId = bo.getData();
        if (sysMenuManageService.hasChildByMenuId(menuId)) {
            warn("存在子菜单,不允许删除");
        }
        if (sysMenuManageService.checkMenuExistRole(menuId)) {
            warn("菜单已分配,不允许删除");
        }
        sysMenuManageService.deleteMenuById(menuId);
    }

}
