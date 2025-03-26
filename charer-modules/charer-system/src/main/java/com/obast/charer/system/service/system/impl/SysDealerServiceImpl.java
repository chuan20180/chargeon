package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.enums.AdminTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.model.system.*;
import com.obast.charer.system.dto.bo.SysDealerBo;
import com.obast.charer.system.dto.vo.SysDealerVo;
import com.obast.charer.system.service.system.ISysDealerService;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.qo.SysDealerQueryBo;
import cn.dev33.satoken.secure.BCrypt;
import com.obast.charer.data.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：合作商管理服务实现
 */
@Service
public class SysDealerServiceImpl implements ISysDealerService {

    @Autowired
    private ISysDealerData sysDealerData;

    @Autowired
    private ISysUserData sysUserData;

    @Autowired
    private ISysRoleData sysRoleData;

    @Autowired
    private ISysRoleMenuData sysRoleMenuData;

    @Autowired
    private ISysUserRoleData sysUserRoleData;

    @Autowired
    private ISysLoginInfoData sysLogininforData;

    @Override
    public Paging<SysDealerVo> queryPageList(PageRequest<SysDealerQueryBo> pageRequest) {
        Paging<SysDealer> pageList = sysDealerData.findPage(pageRequest);
        Paging<SysDealerVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(SysDealer sysDealer: pageList.getRows()) {
            newPageList.getRows().add(fillData(sysDealer));
        }
        return newPageList;
    }

    @Override
    public List<SysDealerVo> queryList(PageRequest<SysDealerQueryBo> pageRequest) {
        List<SysDealer> list = sysDealerData.findList(pageRequest.getData());
        List<SysDealerVo> newList = new ArrayList<>();
        for(SysDealer sysDealer: list) {
            newList.add(fillData(sysDealer));
        }
        return newList;
    }

    @Override
    public SysDealerVo queryDetail(String id) {
        return fillData(sysDealerData.findById(id));
    }

    private SysDealerVo fillData(SysDealer sysDealer) {
        return MapstructUtils.convert(sysDealer, SysDealerVo.class);
    }

    @Override
    @Transactional
    public boolean addDealer(SysDealerBo bo) {
        bo.setStatus(EnableStatusEnum.Enabled);
        SysDealer di = bo.to(SysDealer.class);
        SysDealer sysDealer = sysDealerData.add(di);

        // 根据套餐创建角色
        String roleId = createDealertRole(sysDealer);

        // 创建系统用户
        SysUser user = new SysUser();
        user.setTenantId(sysDealer.getTenantId());
        user.setAgentId(sysDealer.getAgentId());
        user.setDealerId(sysDealer.getId());
        user.setIsTenantAdmin(0);
        user.setIsAgentAdmin(0);
        user.setUserName(bo.getUserName());
        user.setNickName(bo.getUserName());
        user.setPassword(BCrypt.hashpw(bo.getPassword()));
        //user.setDeptId(deptId);
        user.setUserType(AdminTypeEnum.DealerUser);
        user.setStatus(EnableStatusEnum.Enabled);

        user.setPhone(bo.getContactMobile());
        SysUser retUser=sysUserData.save(user);

        // 用户和角色关联表
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(retUser.getId());
        userRole.setRoleId(roleId);

        sysUserRoleData.save(userRole);

        return true;
    }

    @Override
    @Transactional
    public boolean updateDealer(SysDealerBo bo) {
        SysDealer di = bo.to(SysDealer.class);
        sysDealerData.update(di);
        return true;
    }

    @Override
    public void updateStatus(SysDealerBo bo) {
        SysDealer sysDealer = sysDealerData.findById(bo.getId());
        sysDealer.setStatus(bo.getStatus());
        sysDealerData.save(sysDealer);
    }

    @Override
    @Transactional
    public boolean deleteDealer(String id) {
        SysDealer sysDealer = sysDealerData.findById(id);
        if(sysDealer == null) {
            throw new BizException(ErrCode.SYS_DEALER_NOT_FOUND);
        }

        String dealerId = sysDealer.getId();

        //删除角色
        List<SysRole> roles = sysRoleData.findAllByDealerId(dealerId);

        //删除角色菜单
        sysRoleMenuData.deleteByRoleId(roles.stream().map(SysRole::getId).collect(Collectors.toList()));

        //删除角色
        sysRoleData.deleteByIds(roles.stream().map(SysRole::getId).collect(Collectors.toList()));

        //删除系统用户
        List<SysUser> sysUsers = sysUserData.findAllByDealerId(dealerId);
        for(SysUser sysUser: sysUsers) {
            //删除用户角色
            sysUserRoleData.deleteByUserId(sysUser.getId());
            //删除用户
            sysUserData.deleteById(sysUser.getId());
        }

        //删除系统登陆信息
        List<SysLoginInfo> sysLoginInfos = sysLogininforData.findAllByDealerId(dealerId);
        sysLogininforData.deleteByIds(sysLoginInfos.stream().map(SysLoginInfo::getId).collect(Collectors.toList()));


        sysDealerData.deleteById(sysDealer.getId());
        return true;
    }

    @Override
    @Transactional
    public boolean batchDeleteDealer(List<String> ids) {
        for(String dealerId: ids) {
            SysDealer sysDealer = sysDealerData.findById(dealerId);
            if (sysDealer == null) {
                throw new BizException(ErrCode.SYS_DEALER_NOT_FOUND);
            }
            deleteDealer(sysDealer.getId());
        }
        return true;
    }

    private String createDealertRole(SysDealer sysDealer) {

        // 获取套餐菜单id
        List<String> menuIds = sysDealer.getMenuIds();

        // 创建角色
        SysRole role = new SysRole();
        role.setTenantId(sysDealer.getTenantId());
        role.setAgentId(sysDealer.getAgentId());
        role.setRoleName(sysDealer.getName());
        role.setRoleKey(TenantConstants.AGENT_ADMIN_ROLE_KEY);
        role.setRoleSort(1);
        role.setRemark(TenantConstants.AGENT_ADMIN_ROLE_NAME);
        role.setStatus(EnableStatusEnum.Enabled);
        SysRole retRole=sysRoleData.save(role);
        String roleId = retRole.getId();

        // 创建角色菜单
        List<SysRoleMenu> roleMenus = new ArrayList<>(menuIds.size());
        menuIds.forEach(menuId -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        });
        sysRoleMenuData.insertBatch(roleMenus);
        return roleId;
    }
}