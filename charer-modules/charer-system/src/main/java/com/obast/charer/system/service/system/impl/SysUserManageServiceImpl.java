/*
 *
 *  * | Licensed 未经许可不能去掉「OPENIITA」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [OPENIITA]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.UserConstants;
import com.obast.charer.common.enums.AdminTypeEnum;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StreamUtils;
import com.obast.charer.system.dto.bo.SysUserBo;
import com.obast.charer.system.dto.vo.SysUserVo;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.system.service.system.ISysUserManageService;
import com.obast.charer.model.system.SysRole;
import com.obast.charer.model.system.SysUser;
import com.obast.charer.model.system.SysUserPost;
import com.obast.charer.model.system.SysUserRole;
import com.obast.charer.qo.SysRoleQueryBo;
import com.obast.charer.qo.SysUserQueryBo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.obast.charer.data.system.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysUserManageServiceImpl implements ISysUserManageService {

    @Autowired
    private ISysUserData sysUserData;

    @Autowired
    private ISysRoleData sysRoleData;

    @Autowired
    private ISysUserRoleData sysUserRoleData;

    @Autowired
    private ISysUserPostData sysUserPostData;

    @Override
    public Paging<SysUserVo> queryPageList(PageRequest<SysUserQueryBo> pageRequest) {
        Paging<SysUser> pageList = sysUserData.findPage(pageRequest);
        Paging<SysUserVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(SysUser sysUser: pageList.getRows()) {
            newPageList.getRows().add(fillData(sysUser));
        }
        return newPageList;
    }

    @Override
    public List<SysUserVo> queryList(PageRequest<SysUserQueryBo> pageRequest) {
        List<SysUser> list = sysUserData.findList(pageRequest.getData());
        List<SysUserVo> newList = new ArrayList<>();
        for(SysUser sysUser: list) {
            newList.add(fillData(sysUser));
        }
        return newList;
    }

    @Override
    public SysUserVo queryDetail(String id) {
        return fillData(sysUserData.findById(id));
    }

    private SysUserVo fillData(SysUser sysUser) {
        SysUserVo vo = MapstructUtils.convert(sysUser, SysUserVo.class);
        if(vo == null) {
            return null;
        }

        return vo;
    }

    @Override
    public List<SysUserVo> selectUserList(SysUserBo user) {
        return MapstructUtils.convert(sysUserData.findAllByCondition(user.to(SysUser.class)), SysUserVo.class);
    }


    @Override
    public SysUserVo selectUserByUserName(String userName) {
        return MapstructUtils.convert(sysUserData.findByUserName(userName), SysUserVo.class);
    }

    @Override
    public SysUserVo selectUserByPhonenumber(String phone) {
        return MapstructUtils.convert(sysUserData.findByPhone(phone), SysUserVo.class);

    }

    @Override
    public SysUserVo selectUserById(String userId) {
        return MapstructUtils.convert(sysUserData.findById(userId), SysUserVo.class);
    }


    @Override
    public boolean checkUserNameUnique(SysUser sysUser) {
        return sysUserData.checkUserNameUnique(sysUser);
    }

    @Override
    public void checkUserAllowed(SysUserBo user) {
        if (ObjectUtil.isNotNull(user.getId()) && user.isSuperAdmin()) {
            throw new BizException(ErrCode.UNAUTHORIZED_EXCEPTION);
        }
    }

    @Override
    public void checkUserDataScope(String userId) {
        if (ObjectUtil.isNull(userId)) {
            return;
        }
        if (LoginHelper.isSuperAdmin()) {
            return;
        }
        if (ObjectUtil.isNull(sysUserData.findById(userId))) {
            throw new BizException(ErrCode.UNAUTHORIZED_EXCEPTION);
        }
    }

    @Override
    public void insertUser(SysUserBo user) {

        LoginUser loginUser = LoginHelper.getLoginUser();
        switch (loginUser.getUserType()) {
            case SuperAdmin:
                user.setUserType(AdminTypeEnum.PlatformUser);
                break;
            case TenantUser:
                user.setUserType(AdminTypeEnum.TenantUser);
                break;
            case AgentUser:
                user.setUserType(AdminTypeEnum.AgentUser);
                break;
            case DealerUser:
                user.setUserType(AdminTypeEnum.DealerUser);
                break;
            default:
                throw new BizException(ErrCode.SYS_USER_TYPE_ERROR);
        }

        SysUser bo = user.to(SysUser.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        SysUser newUser = sysUserData.add(bo);
        user.setId(newUser.getId());
        // 新增用户岗位关联
        insertUserPost(user, false);
        // 新增用户与角色管理
        insertUserRole(user, false);

    }

    /**
     * 新增用户角色信息
     *
     * @param user  用户对象
     * @param clear 清除已存在的关联数据
     */
    private void insertUserRole(SysUserBo user, boolean clear) {
        this.insertUserRole(user.getId(), user.getRoleIds(), clear);
    }

    /**
     * 新增用户岗位信息
     *
     * @param user  用户对象
     * @param clear 清除已存在的关联数据
     */
    private void insertUserPost(SysUserBo user, boolean clear) {
        List<String> posts = user.getPostIds();
        if (CollectionUtil.isNotEmpty(posts)) {
            if (clear) {
                // 删除用户与岗位关联
                sysUserPostData.deleteByUserId(user.getId());
            }
            // 新增用户与岗位管理
            List<SysUserPost> list = StreamUtils.toList(posts, postId -> {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getId());
                up.setPostId(postId);
                return up;
            });
            sysUserPostData.batchSave(list);
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     * @param clear   清除已存在的关联数据
     */
    private void insertUserRole(String userId, List<String> roleIds, boolean clear) {
        if (CollectionUtil.isNotEmpty(roleIds)) {
            // 判断是否具有此角色的操作权限
            List<SysRole> roles = sysRoleData.findList(new SysRoleQueryBo());
            if (CollUtil.isEmpty(roles)) {
                throw new BizException(ErrCode.UNAUTHORIZED_EXCEPTION);
            }
            List<String> roleList = StreamUtils.toList(roles, SysRole::getId);
            if (!LoginHelper.isSuperAdmin(userId)) {
                roleList.remove(UserConstants.SUPER_ADMIN_ID);
            }
            List<String> canDoRoleList = StreamUtils.filter(roleIds, roleList::contains);
            if (CollUtil.isEmpty(canDoRoleList)) {
                throw new BizException(ErrCode.UNAUTHORIZED_EXCEPTION);
            }
            if (clear) {
                // 删除用户与角色关联
                sysUserRoleData.deleteByUserId(userId);
            }
            // 新增用户与角色管理
            List<SysUserRole> list = StreamUtils.toList(canDoRoleList, roleId -> {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                return ur;
            });
            sysUserRoleData.batchSave(list);
        }
    }

    @Override
    public boolean registerUser(SysUserBo user, String tenantId) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUserBo user) {
        // 新增用户与角色管理
        insertUserRole(user, true);
        // 新增用户与岗位管理
        insertUserPost(user, true);
        SysUser sysUser = MapstructUtils.convert(user, SysUser.class);
        // 防止错误更新后导致的数据误删除
        SysUser ret = sysUserData.save(sysUser);
        if (ret == null) {
            throw new BizException("修改用户" + user.getUserName() + "信息失败");
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(String userId, List<String> roleIds) {
        insertUserRole(userId, roleIds, true);
    }

    @Override
    public int updateUserStatus(String userId, EnableStatusEnum status) {
        SysUser user = sysUserData.findById(userId);
        user.setStatus(status);
        return sysUserData.save(user) != null ? 1 : 0;
    }

    @Override
    public int updateUserProfile(SysUserBo user) {
        SysUser oldUser = sysUserData.findById(user.getId());
        if (ObjectUtil.isNotNull(user.getNickName())) {
            oldUser.setNickName(user.getNickName());
        }
        oldUser.setPhone(user.getPhone());
        oldUser.setEmail(user.getEmail());
        oldUser.setSex(user.getSex());
        return sysUserData.save(oldUser) != null ? 1 : 0;
    }

    @Override
    public boolean updateUserAvatar(String userId, String avatar) {
        SysUser oldUser = sysUserData.findById(userId);
        oldUser.setAvatar(avatar);
        sysUserData.save(oldUser);
        return Boolean.TRUE;
    }

    @Override
    public int resetUserPwd(String userId, String password) {
        SysUser user = sysUserData.findById(userId);
        user.setPassword(password);
        return sysUserData.save(user) != null ? 1 : 0;
    }

    @Override
    @Transactional
    public void deleteUserById(String userId) {
        // 删除用户与角色关联
        sysUserRoleData.deleteByUserId(userId);

        // 删除用户与岗位关联
        sysUserPostData.deleteByUserId(userId);

        //删除用户信息
        sysUserData.deleteById(userId);
    }
}
