package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysRoleMenu;
import com.obast.charer.qo.SysRoleMenuQueryBo;

import java.util.Collection;
import java.util.List;

/**
 * 操作日志数据接口
 *
 * @author sjg
 */
public interface ISysRoleMenuData extends ICommonData<SysRoleMenu, String>, IJPACommonData<SysRoleMenu, SysRoleMenuQueryBo, String> {

    List<String> findMenuListByRoleId(String roleId);

    List<SysRoleMenu> findListByRoleId(String roleId);

    boolean checkMenuExistRole(String menuId);

    long insertBatch(List<SysRoleMenu> list);

    void deleteByRoleId(Collection<String> roleIds);

}
