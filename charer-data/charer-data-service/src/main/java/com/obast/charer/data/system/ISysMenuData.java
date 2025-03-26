package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysMenu;
import com.obast.charer.qo.SysMenuQueryBo;

import java.util.List;

/**
 * 菜单数据接口
 *
 * @author sjg
 */
public interface ISysMenuData extends ICommonData<SysMenu, String>, IJPACommonData<SysMenu, SysMenuQueryBo, String> {


    List<SysMenu> findAllList(SysMenuQueryBo bo);

    List<SysMenu> findPlatformList(SysMenuQueryBo bo);

    List<SysMenu> findTenantList(SysMenuQueryBo bo);

    List<SysMenu> findAgentList(SysMenuQueryBo bo);

    List<SysMenu> findDealerList(SysMenuQueryBo bo);


    List<String> selectMenuPermsByUserId(String userId);

    List<String> selectMenuPermsByRoleId(String roleId);

    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(String userId);

    boolean hasChildByMenuId(String menuId);

    boolean checkMenuNameUnique(SysMenu menu);


    List<String> selectParentIdByMenuIds(List<String> menuIds);

    List<String> findByMenuIdListAndNotParentIdList(List<String> menuIds, List<String> parentIds);
}
