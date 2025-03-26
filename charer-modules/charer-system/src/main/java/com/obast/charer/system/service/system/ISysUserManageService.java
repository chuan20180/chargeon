package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.model.system.SysUser;
import com.obast.charer.system.dto.bo.SysUserBo;
import com.obast.charer.system.dto.vo.SysUserVo;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.qo.SysUserQueryBo;

import java.util.Collection;
import java.util.List;

/**
 * 用户 业务层
 *
 * @author Lion Li
 */
public interface ISysUserManageService {


    Paging<SysUserVo> queryPageList(PageRequest<SysUserQueryBo> pageRequest);

    List<SysUserVo> queryList(PageRequest<SysUserQueryBo> pageRequest);

    SysUserVo queryDetail(String id);

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserVo> selectUserList(SysUserBo user);


    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUserVo selectUserByUserName(String userName);

    /**
     * 通过手机号查询用户
     *
     * @param phonenumber 手机号
     * @return 用户对象信息
     */
    SysUserVo selectUserByPhonenumber(String phonenumber);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUserVo selectUserById(String userId);

    /**
     * 校验用户名称是否唯一
     */
    boolean checkUserNameUnique(SysUser sysUser);


    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    void checkUserAllowed(SysUserBo user);

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    void checkUserDataScope(String userId);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    void insertUser(SysUserBo user);

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean registerUser(SysUserBo user, String tenantId);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUserBo user);

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    void insertUserAuth(String userId, List<String> roleIds);

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 帐号状态
     * @return 结果
     */
    int updateUserStatus(String userId, EnableStatusEnum status);

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUserProfile(SysUserBo user);

    /**
     * 修改用户头像
     *
     * @param userId 用户ID
     * @param avatar 头像地址
     * @return 结果
     */
    boolean updateUserAvatar(String userId, String avatar);

    /**
     * 重置用户密码
     *
     * @param userId   用户ID
     * @param password 密码
     * @return 结果
     */
    int resetUserPwd(String userId, String password);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     */
    void deleteUserById(String userId);


}
