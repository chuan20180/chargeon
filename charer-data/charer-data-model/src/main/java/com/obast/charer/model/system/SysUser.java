package com.obast.charer.model.system;

import com.obast.charer.common.enums.AdminTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 用户信息视图对象 sys_user
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUser extends BaseModel implements Id<String>, Serializable {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户类型
     */
    private AdminTypeEnum userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private EnableStatusEnum status;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 备注
     */
    private String remark;

    /*
     * 是否是租户管理员
     */
    private Integer isTenantAdmin;

    /*
     * 是否是租户管理员
     */
    private Integer isAgentAdmin;

    /**
     * 部门对象
     */
    private SysDept dept;

    /**
     * 角色对象
     */
    private List<SysRole> roles;

    /**
     * 角色组
     */
    private List<String> roleIds;

    /**
     * 岗位组
     */
    private String[] postIds;

    /**
     * 数据权限 当前角色ID
     */
    private String roleId;

}
