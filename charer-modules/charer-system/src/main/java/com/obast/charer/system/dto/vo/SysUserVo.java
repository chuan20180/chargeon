package com.obast.charer.system.dto.vo;

import com.obast.charer.common.enums.AdminTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.DealerModel;
import com.obast.charer.model.system.SysUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.obast.charer.system.dto.vo.system.SysRoleVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;


/**
 * 用户信息视图对象 sys_user
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AutoMapper(target = SysUser.class)
public class SysUserVo  extends DealerModel {

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
    @JsonIgnore
    @JsonProperty
    private String password;

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


    private Integer isAgentAdmin;

    /**
     * 部门对象
     */
    private SysDeptVo dept;

    /**
     * 角色对象
     */
    private List<SysRoleVo> roles;

    /**
     * 角色组
     */
    private String[] roleIds;

    /**
     * 岗位组
     */
    private String[] postIds;

    /**
     * 数据权限 当前角色ID
     */
    private String roleId;


}
