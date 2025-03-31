package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.Constants;
import com.obast.charer.common.constant.UserConstants;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StreamUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.utils.TreeBuildUtils;
import com.obast.charer.common.i18n.util.I18nUtils;
import com.obast.charer.data.system.ISysMenuData;
import com.obast.charer.data.system.ISysRoleData;
import com.obast.charer.data.system.ISysRoleMenuData;
import com.obast.charer.system.dto.bo.SysMenuBo;
import com.obast.charer.system.dto.vo.MetaVo;
import com.obast.charer.system.dto.vo.RouterVo;
import com.obast.charer.system.dto.vo.SysMenuVo;
import com.obast.charer.system.service.system.ISysMenuManageService;
import com.obast.charer.model.system.SysMenu;
import com.obast.charer.model.system.SysRole;
import com.obast.charer.qo.SysMenuQueryBo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class SysMenuManageServiceImpl implements ISysMenuManageService {

    private final ISysMenuData sysMenuData;

    private final ISysRoleData sysRoleData;
    private final ISysRoleMenuData iSysRoleMenuData;


    @Override
    public Paging<SysMenuVo> queryPageList(PageRequest<SysMenuQueryBo> pageRequest) {
        Paging<SysMenu> pageList = sysMenuData.findPage(pageRequest);
        Paging<SysMenuVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(SysMenu sysMenu: pageList.getRows()) {
            newPageList.getRows().add(fillData(sysMenu));
        }
        return newPageList;
    }

    @Override
    public List<SysMenuVo> queryAllList(SysMenuQueryBo bo) {
        List<SysMenu> list = sysMenuData.findAllList(bo);
        List<SysMenuVo> newList = new ArrayList<>();
        for(SysMenu sysMenu: list) {
            newList.add(fillData(sysMenu));
        }
        return newList;
    }

    @Override
    public List<SysMenuVo> queryPlatformList(SysMenuQueryBo bo) {
        List<SysMenu> list = sysMenuData.findPlatformList(bo);
        List<SysMenuVo> newList = new ArrayList<>();
        for(SysMenu sysMenu: list) {
            newList.add(fillData(sysMenu));
        }
        return newList;
    }

    @Override
    public List<SysMenuVo> queryTenantList(SysMenuQueryBo bo) {
        List<SysMenu> list = sysMenuData.findTenantList(bo);
        List<SysMenuVo> newList = new ArrayList<>();
        for(SysMenu sysMenu: list) {
            newList.add(fillData(sysMenu));
        }
        return newList;
    }

    @Override
    public List<SysMenuVo> queryAgentList(SysMenuQueryBo bo) {
        List<SysMenu> list = sysMenuData.findAgentList(bo);
        List<SysMenuVo> newList = new ArrayList<>();
        for(SysMenu sysMenu: list) {
            newList.add(fillData(sysMenu));
        }
        return newList;
    }

    @Override
    public List<SysMenuVo> queryDealerList(SysMenuQueryBo bo) {
        List<SysMenu> list = sysMenuData.findDealerList(bo);
        List<SysMenuVo> newList = new ArrayList<>();
        for(SysMenu sysMenu: list) {
            newList.add(fillData(sysMenu));
        }
        return newList;
    }


    @Override
    public SysMenuVo queryDetail(String id) {
        return fillData(sysMenuData.findById(id));
    }



    private SysMenuVo fillData(SysMenu sysMenu) {
        return MapstructUtils.convert(sysMenu, SysMenuVo.class);
    }


    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(String userId) {
        List<String> perms = sysMenuData.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(StringUtils.splitList(perm.trim()));
            }
        }
        return permsSet;
    }

    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByRoleId(String roleId) {
        List<String> perms = sysMenuData.selectMenuPermsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(StringUtils.splitList(perm.trim()));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(String userId) {
        List<SysMenu> menus = null;
        if (LoginHelper.isSuperAdmin(userId)) {
            menus = sysMenuData.selectMenuTreeAll();
        } else {
            menus = sysMenuData.selectMenuTreeByUserId(userId);
        }

        //log.debug("菜单 {}", menus);
        return getChildPerms(menus, String.valueOf(0));
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<String> selectMenuListByRoleId(String roleId) {
        SysRole role = sysRoleData.findById(roleId);
        Boolean flag = role.getMenuCheckStrictly();
        return sysRoleData.selectMenuListByRoleId(roleId, flag != null && flag);
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQueryParam());
            router.setMeta(new MetaVo(I18nUtils.convert(menu.getMenuName()), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (CollUtil.isNotEmpty(cMenus) && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(I18nUtils.convert(menu.getMenuName()), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
                children.setQuery(menu.getQueryParam());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().equals("0") && isInnerLink(menu)) {
                router.setMeta(new MetaVo(I18nUtils.convert(menu.getMenuName()), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(I18nUtils.convert(menu.getMenuName()), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenu menu) {
        return menu.getParentId().equals("0") && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (!menu.getParentId().equals("0") && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (menu.getParentId().equals("0") && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && !menu.getParentId().equals("0") && isInnerLink(menu)) {
            component = UserConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<Tree<String>> buildMenuTreeSelect(List<SysMenuVo> menus) {
        if (CollUtil.isEmpty(menus)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(menus, (menu, tree) ->
                tree.setId(menu.getId())
                        .setParentId(menu.getParentId())
                        .setName(I18nUtils.convert(menu.getMenuName()))
                        .setWeight(menu.getOrderNum()));
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public SysMenuVo selectMenuById(String menuId) {
        return sysMenuData.findById(menuId).to(SysMenuVo.class);
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean hasChildByMenuId(String menuId) {
        return sysMenuData.hasChildByMenuId(menuId);
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenu menu) {
        return menu.getIsFrame().equals(UserConstants.NO_FRAME) && StringUtils.ishttp(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenu menu) {
        return !menu.getParentId().equals("0") && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public boolean checkMenuExistRole(String menuId) {
        return iSysRoleMenuData.checkMenuExistRole(menuId);
    }

    /**
     * 新增保存菜单信息
     *
     * @param bo 菜单信息
     */
    @Override
    public void insertMenu(SysMenuBo bo) {
        sysMenuData.save(bo.to(SysMenu.class));
    }

    /**
     * 修改保存菜单信息
     *
     * @param bo 菜单信息
     */
    @Override
    public void updateMenu(SysMenuBo bo) {
        sysMenuData.save(bo.to(SysMenu.class));
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     */
    @Override
    public void deleteMenuById(String menuId) {
        sysMenuData.deleteById(menuId);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param bo 菜单信息
     * @return 结果
     */
    @Override
    public boolean checkMenuNameUnique(SysMenuBo bo) {
        return sysMenuData.checkMenuNameUnique(bo.to(SysMenu.class));
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    private List<SysMenu> getChildPerms(List<SysMenu> list, String parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (Objects.equals(t.getParentId(), parentId)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = StreamUtils.filter(list, n -> n.getParentId().equals(t.getId()));
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            // 判断是否有子节点
            if (list.stream().anyMatch(n -> n.getParentId().equals(tChild.getId()))) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 内链域名特殊字符替换
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, "."},
                new String[]{"", "", "", "/"});
    }
}
