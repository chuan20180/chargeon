package com.obast.charer.system.service.platform.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.ObjectUtil;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.enums.AdminTypeEnum;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.system.*;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.*;
import com.obast.charer.qo.SysTenantQueryBo;
import com.obast.charer.system.dto.bo.SysTenantBo;
import com.obast.charer.system.dto.vo.SysAgentVo;
import com.obast.charer.system.dto.vo.tenant.SysTenantVo;
import com.obast.charer.system.service.platform.ISysTenantService;
import com.obast.charer.system.service.system.ISysAgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户Service业务层处理
 *
 * @author Michelle.Chung
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysTenantServiceImpl implements ISysTenantService {

    private final ISysTenantData sysTenantData;

    private final ISysTenantPackageData sysTenantPackageData;

    private final ISysUserData sysUserData;

    private final ISysRoleData sysRoleData;

    private final ISysRoleMenuData sysRoleMenuData;

    private final ISysDeptData sysDeptData;

    private final ISysUserRoleData sysUserRoleData;

    private final ISysUserPostData sysUserPostData;

    private final ISysAppData sysAppData;

    private final ISysConfigData sysConfigData;

    private final ISysLoginInfoData sysLoginInfoData;

    private final ISysAgentService sysAgentService;

    @Override
    public SysTenantVo queryById(String id) {
        return sysTenantData.findById(id).to(SysTenantVo.class);
    }

    /**
     * 基于租户ID查询租户
     */
//    @Cacheable(cacheNames = CacheNames.SYS_TENANT, key = "#tenantId")
    @Override
    public SysTenantVo queryByTenantId(String tenantId) {
        SysTenant sysTenant = new SysTenant();
        sysTenant.setTenantId(tenantId);
        SysTenant tenant = sysTenantData.findOneByCondition(sysTenant);
        return MapstructUtils.convert(tenant,SysTenantVo.class);
    }

    @Override
    public Paging<SysTenantVo> queryPageList(PageRequest<SysTenantQueryBo> query) {
        return sysTenantData.findPage(query).to(SysTenantVo.class);
    }

    @Override
    public List<SysTenantVo> queryList(SysTenantQueryBo bo) {
        return MapstructUtils.convert(sysTenantData.findList(bo), SysTenantVo.class);
    }

    @Override
    @Transactional
    public void insertByBo(SysTenantBo bo) {
        bo.setStatus(EnableStatusEnum.Enabled);

        if(!checkCompanyNameUnique(bo)) {
            throw new BizException(ErrCode.TENANT_COMPANY_NAME_ALREADY_USED);
        }

        if(!checkUserNameUnique(bo)) {
            throw new BizException(ErrCode.TENANT_USERNAME_ALREADY_USED);
        }

        SysTenant sysTenant=sysTenantData.add(bo.to(SysTenant.class));

        // 根据套餐创建角色
        String roleId = createTenantRole(sysTenant.getTenantId(), bo.getPackageId());

        // 创建部门: 公司名是部门名称
        SysDept dept = new SysDept();
        dept.setTenantId(sysTenant.getTenantId());
        dept.setDeptName(bo.getCompanyName());
        dept.setParentId("0");
        dept.setAncestors("0");
        dept.setPhone(bo.getContactPhone());
        dept.setStatus(EnableStatusEnum.Enabled);
        SysDept retDept =sysDeptData.save(dept);
        String deptId = retDept.getId();


        // 创建系统用户
        SysUser user = new SysUser();
        user.setTenantId(sysTenant.getTenantId());
        user.setIsTenantAdmin(1);
        user.setUserName(bo.getUsername());
        user.setNickName(bo.getUsername());
        user.setPassword(BCrypt.hashpw(bo.getPassword()));
        user.setDeptId(deptId);
        user.setUserType(AdminTypeEnum.TenantUser);
        user.setStatus(EnableStatusEnum.Enabled);
        user.setRemark(TenantConstants.TENANT_ADMIN_ROLE_NAME);
        user.setPhone(bo.getContactPhone());
        SysUser retUser=sysUserData.save(user);

        //新增系统用户后，默认当前用户为部门的负责人
        SysDept updateDept =sysDeptData.findById(retDept.getId());
        updateDept.setLeader(retUser.getUserName());
        sysDeptData.save(updateDept);

        // 用户和角色关联表
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(retUser.getId());
        userRole.setRoleId(roleId);
        userRole.setTenantId(String.valueOf(sysTenant.getTenantId()));
        sysUserRoleData.save(userRole);

        String defaultTenantId = TenantConstants.DEFAULT_TENANT_ID;

        SysConfig querySysConfig=new SysConfig();
        querySysConfig.setTenantId(defaultTenantId);
        List<SysConfig> sysConfigList = sysConfigData.findAllByCondition(querySysConfig);

        for (SysConfig config : sysConfigList) {
            config.setId(null);
            config.setTenantId(sysTenant.getTenantId());
        }
        sysConfigData.batchSave(sysConfigList);
    }

    /**
     * 根据租户菜单创建租户角色
     *
     * @param tenantId  租户编号
     * @param packageId 租户套餐id
     * @return 角色id
     */
    private String createTenantRole(String tenantId, String packageId) {
        // 获取租户套餐
        SysTenantPackage tenantPackage = sysTenantPackageData.findById(packageId);
        if (ObjectUtil.isNull(tenantPackage)) {
            throw new BizException(ErrCode.TENANT_PACKAGE_NOT_FOUND);
        }
        // 获取套餐菜单id
        List<String> menuIds = tenantPackage.getMenuIds();

        // 创建角色
        SysRole role = new SysRole();
        role.setTenantId(tenantId);
        role.setRoleName(TenantConstants.TENANT_ADMIN_ROLE_NAME);
        role.setRoleKey(TenantConstants.TENANT_ADMIN_ROLE_KEY);
        role.setRoleSort(1);
        role.setRemark(TenantConstants.TENANT_ADMIN_ROLE_NAME);
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

    @Override
    public void updateByBo(SysTenantBo bo) {
        if(!checkCompanyNameUnique(bo)) {
            throw new BizException(ErrCode.TENANT_COMPANY_NAME_ALREADY_USED);
        }
        sysTenantData.update(bo.to(SysTenant.class));
    }

    @Override
    public void updateStatus(SysTenantBo bo) {
        SysTenant tenantDataById = sysTenantData.findById(bo.getId());
        tenantDataById.setStatus(bo.getStatus());
        sysTenantData.save(tenantDataById);
    }

    @Override
    public void checkTenantAllowed(String tenantId) {
        if (ObjectUtil.isNotNull(tenantId) && TenantConstants.DEFAULT_TENANT_ID.equals(tenantId)) {
            throw new BizException(ErrCode.TENANT_UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        SysTenant sysTenant = sysTenantData.findById(id);
        if (ObjectUtil.isNull(sysTenant)) {
            throw new BizException(ErrCode.TENANT_NOT_FOUND);
        }

        String tenantId = sysTenant.getId();

        if(tenantId.equals(TenantConstants.DEFAULT_TENANT_ID)) {
            throw new BizException(ErrCode.TENANT_UNAUTHORIZED);
        }

        //检查是否存在代理商
        List<SysAgentVo> sysAgentVos = sysAgentService.queryListByTenantId(tenantId);
        if(!sysAgentVos.isEmpty()) {
            throw new BizException(ErrCode.TENANT_DELETE_EXIST_AGENT);
        }

        //获取租户角色
        List<SysRole> roles = sysRoleData.findAllByTenantId(tenantId);
        if(ObjectUtil.isNotEmpty(roles)) {
            List<String> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());


            //删除角色菜单
            sysRoleMenuData.deleteByRoleId(roleIds);

            //删除角色
            sysRoleData.deleteByIds(roleIds);
        }

        //查询租户部门
        List<SysDept> depts = sysDeptData.findAllByTenantId(tenantId);
        if(ObjectUtil.isNotEmpty(depts)) {
            List<String> deptIds = depts.stream().map(SysDept::getId).collect(Collectors.toList());
            sysDeptData.deleteByIds(deptIds);
        }

        //删除系统用户
        List<SysUser> sysUsers = sysUserData.findAllByTenantId(tenantId);
        if(ObjectUtil.isNotEmpty(sysUsers)) {
            for(SysUser sysUser: sysUsers) {
                //删除用户角色
                sysUserRoleData.deleteByUserId(sysUser.getId());
                //删除用户职位
                sysUserPostData.deleteByUserId(sysUser.getId());
                //删除用户
                sysUserData.deleteById(sysUser.getId());
            }
        }

        //删除系统配置
        List<SysConfig> sysConfigs = sysConfigData.findAllByTenantId(tenantId);
        if(ObjectUtil.isNotEmpty(sysConfigs)) {
            List<String> configIds = sysConfigs.stream().map(SysConfig::getId).collect(Collectors.toList());
            sysConfigData.deleteByIds(configIds);
        }

        //删除系统APP
        List<SysApp> sysApps = sysAppData.findAllByTenantId(tenantId);
        if(ObjectUtil.isNotEmpty(sysApps)) {
            List<String> appIds = sysApps.stream().map(SysApp::getId).collect(Collectors.toList());
            sysAppData.deleteByIds(appIds);
        }

        //删除系统登陆信息
        List<SysLoginInfo> sysLogins = sysLoginInfoData.findAllByTenantId(tenantId);
        if(ObjectUtil.isNotEmpty(sysLogins)) {
            List<String> loginIds = sysLogins.stream().map(SysLoginInfo::getId).collect(Collectors.toList());
            sysLoginInfoData.deleteByIds(loginIds);
        }

        //删除代理信息
        List<SysAgentVo> sysAgents = sysAgentService.queryListByTenantId(tenantId);
        if(ObjectUtil.isNotEmpty(sysAgents)) {
            for(SysAgentVo sysAgent: sysAgents) {
                sysAgentService.deleteAgent(sysAgent.getId());
            }
        }

        //删除租户
        sysTenantData.deleteById(id);
    }

    @Override
    public boolean checkCompanyNameUnique(SysTenantBo bo) {
        return sysTenantData.checkCompanyNameUnique(bo.to(SysTenant.class));
    }

    @Override
    public boolean checkUserNameUnique(SysTenantBo bo) {
        SysUser sysUserBo = new SysUser();
        sysUserBo.setUserName(bo.getUsername());
        return sysUserData.checkUserNameUnique(sysUserBo);
    }

    @Override
    public boolean checkAccountBalance(String tenantId) {
        SysTenantVo tenant = this.queryByTenantId(tenantId);
        // 如果余额为-1不限制
        if (tenant.getAccountCount() == -1) {
            return true;
        }
        Long userNumber = sysUserData.count();
        // 如果余额大于0代表还有可用名额
        return tenant.getAccountCount() - userNumber > 0;
    }

    @Override
    public boolean checkExpireTime(String tenantId) {
        return false;
    }

    @Override
    public Boolean syncTenantPackage(String tenantId, String packageId) {
        return false;
    }

    @Override
    public Boolean resetAdminPwd(String tenantId, String password) {
        return false;
    }


}
