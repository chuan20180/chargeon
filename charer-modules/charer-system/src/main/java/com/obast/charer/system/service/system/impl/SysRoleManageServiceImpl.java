package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.model.RoleDTO;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StreamUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.system.ISysRoleData;
import com.obast.charer.data.system.ISysRoleMenuData;
import com.obast.charer.data.system.ISysUserRoleData;
import com.obast.charer.system.dto.bo.SysRoleBo;
import com.obast.charer.system.dto.vo.system.SysRoleVo;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.system.service.system.ISysRoleManageService;
import com.obast.charer.model.system.SysRole;
import com.obast.charer.model.system.SysRoleMenu;
import com.obast.charer.model.system.SysUserRole;
import com.obast.charer.qo.SysRoleQueryBo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色 业务层处理
 *
 * @author Lion Li
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysRoleManageServiceImpl implements ISysRoleManageService {

    private final ISysRoleData sysRoleData;

    private final ISysRoleMenuData sysRoleMenuData;
    private final ISysUserRoleData iSysUserRoleData;

    @Override
    public Paging<SysRoleVo> queryPageList(PageRequest<SysRoleQueryBo> pageRequest) {
        Paging<SysRole> pageList = sysRoleData.findPage(pageRequest);
        Paging<SysRoleVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(SysRole sysRole: pageList.getRows()) {
            newPageList.getRows().add(fillData(sysRole));
        }
        return newPageList;
    }

    @Override
    public List<SysRoleVo> queryList(PageRequest<SysRoleQueryBo> pageRequest) {
        List<SysRole> list = sysRoleData.findList(pageRequest.getData());
        List<SysRoleVo> newList = new ArrayList<>();
        for(SysRole sysRole: list) {
            newList.add(fillData(sysRole));
        }
        return newList;
    }

    @Override
    public SysRoleVo queryDetail(String id) {
        return fillData(sysRoleData.findById(id));
    }


    private SysRoleVo fillData(SysRole sysRole) {
        SysRoleVo vo = MapstructUtils.convert(sysRole, SysRoleVo.class);
        if(vo == null) {
            return vo;
        }
        List<String> menuIds = sysRoleMenuData.findMenuListByRoleId(vo.getId());
        vo.setMenuIds(menuIds);
        return vo;
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRoleVo> selectRolesByUserId(String userId) {
        List<SysRole> sysRoles = sysRoleData.findListByUserId(userId);
        List<SysRole> roles = sysRoleData.findList(new SysRoleQueryBo());
        for (SysRole role : roles) {
            for (SysRole sysRole : sysRoles) {
                if (Objects.equals(role.getId(), sysRole.getId())) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return MapstructUtils.convert(roles, SysRoleVo.class);
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeyByUserId(String userId) {
        List<SysRole> perms = sysRoleData.findListByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (ObjectUtil.isNotNull(perm)) {
                permsSet.addAll(StringUtils.splitList(perm.getRoleKey().trim()));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRoleVo> selectRoleAll() {
        return MapstructUtils.convert(sysRoleData.findList(new SysRoleQueryBo()), SysRoleVo.class);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRoleVo selectRoleById(String roleId) {
        return sysRoleData.findById(roleId).to(SysRoleVo.class);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleNameUnique(SysRoleBo role) {
        return sysRoleData.checkRoleNameUnique(MapstructUtils.convert(role, SysRole.class));
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleKeyUnique(SysRoleBo role) {
        return sysRoleData.checkRoleKeyUnique(MapstructUtils.convert(role, SysRole.class));
    }

    /**
     * 校验角色是否允许操作
     *
     * @param roleId 角色ID
     */
    @Override
    public void checkRoleAllowed(String roleId) {
        if (ObjectUtil.isNotNull(roleId) && LoginHelper.isSuperAdmin(roleId)) {
            throw new BizException("不允许操作超级管理员角色");
        }
    }

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    @Override
    public void checkRoleDataScope(String roleId) {
        if (ObjectUtil.isNull(roleId)) {
            return;
        }
        if (LoginHelper.isSuperAdmin()) {
            return;
        }
        List<SysRole> roles = sysRoleData.findList(new SysRoleQueryBo(roleId));
        if (CollUtil.isEmpty(roles)) {
            throw new BizException("没有权限访问角色数据！");
        }

    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public long countUserRoleByRoleId(String roleId) {
        return iSysUserRoleData.countUserRoleByRoleId(roleId);
    }

    /**
     * 新增保存角色信息
     *
     * @param bo 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRoleBo bo) {
        SysRole role = sysRoleData.add(bo.to(SysRole.class));
        bo.setId(role.getId());
        return insertRoleMenu(bo);
    }

    /**
     * 修改保存角色信息
     *
     * @param bo 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(SysRoleBo bo) {
        // 修改角色信息
        sysRoleData.update(bo.to(SysRole.class));
        // 删除角色与菜单关联
        sysRoleMenuData.deleteByRoleId(List.of(bo.getId()));
        return insertRoleMenu(bo);
    }

    /**
     * 修改角色状态
     *
     * @param roleId 角色ID
     * @param status 角色状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String roleId, EnableStatusEnum status) {
        SysRole sysRole = sysRoleData.findById(roleId);
        sysRole.setStatus(status);
        sysRoleData.save(sysRole);
        cleanOnlineUserByRole(sysRole.getId());
    }


    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    private int insertRoleMenu(SysRoleBo role) {
        long rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<>();
        for (String menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            rows = sysRoleMenuData.insertBatch(list);
        }
        return Integer.parseInt(rows + "");
    }


    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleById(String roleId) {
        // 删除角色与菜单关联
        sysRoleMenuData.deleteByRoleId(List.of(roleId));
        sysRoleData.deleteById(roleId);
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleByIds(Collection<String> roleIds) {
        for (String roleId : roleIds) {
            checkRoleAllowed(roleId);
            checkRoleDataScope(roleId);
            SysRole role = sysRoleData.findById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new BizException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }

        // 删除角色与菜单关联
        sysRoleMenuData.deleteByRoleId(roleIds);
        sysRoleData.deleteByIds(roleIds);
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAuthUser(SysUserRole userRole) {
        iSysUserRoleData.delete(userRole.getRoleId(), List.of(userRole.getUserId()));
        cleanOnlineUserByRole(userRole.getRoleId());
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAuthUsers(String roleId, String[] userIds) {
       iSysUserRoleData.delete(roleId, Arrays.asList(userIds));
        cleanOnlineUserByRole(roleId);
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要授权的用户数据ID
     */
    @Override
    public void insertAuthUsers(String roleId, String[] userIds) {
        // 新增用户与角色管理
        long rows = 1;
        List<SysUserRole> list = StreamUtils.toList(List.of(userIds), userId -> {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            return ur;
        });
        if (CollUtil.isNotEmpty(list)) {
            rows = iSysUserRoleData.insertBatch(list);
        }
        if (rows > 0) {
            cleanOnlineUserByRole(roleId);
        }
    }

    @Override
    public void cleanOnlineUserByRole(String roleId) {
        List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);

        log.debug("清空在线用户{}", keys);

        if (CollUtil.isEmpty(keys)) {
            return;
        }
        // 角色关联的在线用户量过大会导致redis阻塞卡顿 谨慎操作
        keys.parallelStream().forEach(key -> {
            String token = StringUtils.substringAfterLast(key, ":");

            log.debug("清空在线用户 token: {}", token);
            // 如果已经过期则跳过
            if (StpUtil.stpLogic.getTokenActivityTimeoutByToken(token) < -1) {
                return;
            }
            LoginUser loginUser = LoginHelper.getLoginUser(token);

            log.debug("清空在线用户 loginUser: {}", loginUser);
            if(Objects.isNull(loginUser)||CollUtil.isEmpty(loginUser.getRoles())){
                return;
            }

            List<String> roleIds = loginUser.getRoles().stream().map(RoleDTO::getId).collect(Collectors.toList());
            log.debug("清空在线用户 参数列表: roleId: {}, roleIds: {}", roleId, roleIds);
            if (loginUser.getRoles().stream().anyMatch(r -> r.getId().equals(roleId))) {

                log.debug("清空在线用户 找到了: role: {}， {}", roleId, token);
                StpUtil.logoutByTokenValue(token);
            }
        });
    }
}
