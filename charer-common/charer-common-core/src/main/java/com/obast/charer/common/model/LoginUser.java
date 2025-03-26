package com.obast.charer.common.model;

import com.obast.charer.common.enums.LogicTypeEnum;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.enums.AdminTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 登录用户身份权限
 *
 * @author Lion Li
 */

@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    LogicTypeEnum logicType;

    private String userId;

    private String userName;

    private String tenantId;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;


    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 语言
     */
    private String language;

    /**
     * 版本
     */
    private String version;

    /**
     * 设备型号
     */
    private String device;

    /**
     * 登陆平台
     */
    private PlatformTypeEnum platform;

    /********************************************************************
     * customer 部分
     *********************************************************************/
    private String customerLoginId;
    private String customerDn;


    /********************************************************************
     * admin 部分
     *********************************************************************/
    private Integer isTenantAdmin;
    private Integer isAgentAdmin;
    private String deptId;
    private String deptName;
    private String dealerId;
    private String agentId;
    private AdminTypeEnum userType;
    private Long expireTime;

    /**
     * 菜单权限
     */
    private Set<String> menuPermission;

    /**
     * 角色权限
     */
    private Set<String> rolePermission;


    /**
     * 角色对象
     */
    private List<RoleDTO> roles;


    /**
     * 获取登录id
     */
    public String getLoginId() {
        if (logicType == null) {
            throw new IllegalArgumentException("用户类型不能为空");
        }
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        return logicType.name() + ":" + userId;
    }

    /**
     * 根据loginId构造loginUser对象
     *
     * @param loginId 登录id
     * @return LoginUser
     * @see LoginUser::getLoginId
     */
    public static LoginUser from(String loginId) {
        if (StringUtils.isBlank(loginId)) {
            return null;
        }
        String[] split = loginId.split(":");
        if (split.length < 2) {
            return null;
        }

        LoginUser user = new LoginUser();
        user.setLogicType(LogicTypeEnum.valueOf(split[0]));
        user.setUserId(String.valueOf(Long.parseLong(split[1])));
        return user;
    }
}
