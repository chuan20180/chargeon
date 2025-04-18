package com.obast.charer.common.satoken.util;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.obast.charer.common.constant.UserConstants;
import com.obast.charer.common.enums.AdminTypeEnum;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.common.model.LoginUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录鉴权助手
 * <p>
 * user_type 为 用户类型 同一个用户表 可以有多种用户类型 例如 pc,app
 * deivce 为 设备类型 同一个用户类型 可以有 多种设备类型 例如 web,ios
 * 可以组成 用户类型与设备类型多对多的 权限灵活控制
 * <p>
 * 多用户体系 针对 多种用户类型 但权限控制不一致
 * 可以组成 多用户类型表与多设备类型 分别控制权限
 *
 * @author Lion Li
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {

    public static final String LOGIN_USER_KEY = "loginUser";

    public static final String USER_KEY = "userId";


    /* *********************************************************************************************************
     * 平台部分
     * *********************************************************************************************************/
    /**
     *
     * @param loginUser 登录用户信息
     */
    public static void loginByPlatform(LoginUser loginUser, PlatformTypeEnum platformType) {
        SaStorage storage = SaHolder.getStorage();
        storage.set(LOGIN_USER_KEY, loginUser);
        storage.set(USER_KEY, loginUser.getUserId());
        SaLoginModel model = new SaLoginModel();
        if (ObjectUtil.isNotNull(platformType)) {
            model.setDevice(platformType.getData());
        }
        StpUtil.login(loginUser.getLoginId(),
                model

                        .setExtra(USER_KEY, loginUser.getUserId())

        ) ;
        StpUtil.getTokenSession().set(LOGIN_USER_KEY, loginUser);
    }

    /**
     * 获取用户(多级缓存)
     */
    public static LoginUser getLoginUser() {
        LoginUser loginUser = null;
        Object saCache=SaHolder.getStorage().get(LOGIN_USER_KEY);
        if (saCache != null) {
            loginUser=(LoginUser) saCache;
            return loginUser;
        }
        SaSession cache=StpUtil.getTokenSession();
        if (cache != null) {
            loginUser= (LoginUser) cache.get(LOGIN_USER_KEY);
            return loginUser;
        }
        SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
        return loginUser;
    }


    /**
     * 获取用户基于token
     */
    public static LoginUser getLoginUser(String token) {
        return (LoginUser) StpUtil.getTokenSessionByToken(token).get(LOGIN_USER_KEY);
    }


    /**
     * 获取用户id
     */
    public static String getUserId() {
        try {
            LoginUser user = LoginUser.from(StpUtil.getLoginIdAsString());
            if (user == null) {
                return null;
            }
            return user.getUserId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取部门ID
     */
    public static String getDeptId() {
        return getLoginUser().getDeptId();
    }

    /**
     * 获取用户账户
     */
    public static String getUserName() {
        return getLoginUser().getUserName();
    }

    /**
     * 获取用户类型
     */
    public static AdminTypeEnum getUserType() {
        String loginId = StpUtil.getLoginIdAsString();
        return AdminTypeEnum.valueOf(loginId);
    }

    /**
     * 是否为超级管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isSuperAdmin(String userId) {
        return UserConstants.SUPER_ADMIN_ID.equals(userId);
    }

    public static boolean isSuperAdmin() {
        return isSuperAdmin(getUserId());
    }



}
