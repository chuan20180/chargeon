package com.obast.charer.common.satoken;

import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.satoken.util.LoginHelper;
import cn.dev33.satoken.stp.StpInterface;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token 权限管理实现类
 *
 * @author Lion Li
 */
@Slf4j
public class SaPermissionImpl implements StpInterface {

    /**
     * 获取菜单权限列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();

        log.debug("获取菜单权限列表 {}", loginUser.getMenuPermission());

        return new ArrayList<>(loginUser.getMenuPermission());


//        UserType userType = UserType.getUserType(loginUser.getUserType());
//        if (userType == UserType.SYS_USER) {
//            return new ArrayList<>(loginUser.getMenuPermission());
//        } else if (userType == UserType.APP_USER) {
//            List<String> perms = new ArrayList<>();
//            perms.add("*:*:*");
//            return perms;
//        }
//        return new ArrayList<>();
    }

    /**
     * 获取角色权限列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();

        log.debug("获取角色权限列表 {}", loginUser.getRolePermission());
        return new ArrayList<>(loginUser.getRolePermission());
//        UserType userType = UserType.getUserType(loginUser.getUserType());
//        if (userType == UserType.SYS_USER) {
//            return new ArrayList<>(loginUser.getRolePermission());
//        } else if (userType == UserType.APP_USER) {
//            // 其他端 自行根据业务编写
//        }
//        return new ArrayList<>();
    }
}
