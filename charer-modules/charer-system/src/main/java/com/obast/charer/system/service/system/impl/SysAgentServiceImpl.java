package com.obast.charer.system.service.system.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.ObjectUtil;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.enums.AdminTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.IStationData;
import com.obast.charer.data.system.*;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.station.Station;
import com.obast.charer.model.system.*;
import com.obast.charer.qo.SysAgentQueryBo;
import com.obast.charer.system.dto.bo.SysAgentBo;
import com.obast.charer.system.dto.vo.SysAgentVo;
import com.obast.charer.system.dto.vo.station.StationVo;
import com.obast.charer.system.service.system.ISysAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysAgentServiceImpl implements ISysAgentService {

    @Autowired
    private ISysAgentData sysAgentData;


    @Autowired
    private ISysUserData sysUserData;

    @Autowired
    private ISysDeptData sysDeptData;

    @Autowired
    private ISysRoleData sysRoleData;

    @Autowired
    private ISysRoleMenuData sysRoleMenuData;

    @Autowired
    private ISysUserRoleData sysUserRoleData;

    @Autowired
    private ISysAgentStationData sysAgentStationData;

    @Autowired
    private ISysAgentStationDealerData sysAgentStationDealerData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private ISysLoginInfoData sysLogininforData;

    @Autowired
    private ISysDealerData sysDealerData;


    @Override
    public Paging<SysAgentVo> queryPageList(PageRequest<SysAgentQueryBo> pageRequest) {
        Paging<SysAgent> pageList = sysAgentData.findPage(pageRequest);
        Paging<SysAgentVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(SysAgent sysAgent: pageList.getRows()) {
            newPageList.getRows().add(fillData(sysAgent));
        }
        return newPageList;
    }

    @Override
    public List<SysAgentVo> queryList(PageRequest<SysAgentQueryBo> pageRequest) {
        List<SysAgent> list = sysAgentData.findList(pageRequest.getData());
        List<SysAgentVo> newList = new ArrayList<>();
        for(SysAgent sysAgent: list) {
            newList.add(fillData(sysAgent));
        }
        return newList;
    }

    @Override
    public List<SysAgentVo> queryListByTenantId(String tenantId) {
        List<SysAgent> list = sysAgentData.findListByTenantId(tenantId);
        List<SysAgentVo> newList = new ArrayList<>();
        for(SysAgent sysAgent: list) {
            newList.add(fillData(sysAgent));
        }
        return newList;
    }

    @Override
    public SysAgentVo queryDetail(String id) {
        return fillData(sysAgentData.findById(id));
    }


    private SysAgentVo fillData(SysAgent sysAgent) {
        SysAgentVo vo = MapstructUtils.convert(sysAgent, SysAgentVo.class);
        if(vo == null) {
            return null;
        }

        List<SysAgentStation> sysAgentStations = sysAgentStationData.findByAgentId(sysAgent.getId());
        if(sysAgentStations != null) {
            List<String> stationIds = sysAgentStations.stream().map(SysAgentStation::getStationId).collect(Collectors.toList());
            if(!stationIds.isEmpty()) {
                vo.setStations(MapstructUtils.convert(stationData.findByIds(stationIds), StationVo.class));
            }
        }

        return vo;
    }

    @Override
    @Transactional
    public boolean addAgent(SysAgentBo bo) {
        bo.setStatus(EnableStatusEnum.Enabled);

        if(bo.getTenantProfitPercent() == null) {
            throw new BizException(ErrCode.SYS_AGENT_TENANT_PROFIT_PERCENT_EMPTY);
        }

        if(bo.getTenantProfitPercent().compareTo(new BigDecimal(0)) < 0 || bo.getTenantProfitPercent().compareTo(new BigDecimal(1)) > 0) {
            throw new BizException(ErrCode.SYS_AGENT_TENANT_PROFIT_PERCENT_INVALID);
        }

        SysAgent sysAgent =  sysAgentData.add(bo.to(SysAgent.class));

        // 根据套餐创建角色
        String roleId = createAgentRole(sysAgent);

        // 创建部门: 公司名是部门名称
        SysDept dept = new SysDept();
        dept.setTenantId(sysAgent.getTenantId());
        dept.setAgentId(sysAgent.getId());
        dept.setDeptName(sysAgent.getName());
        dept.setParentId("0");
        dept.setAncestors("0");
        dept.setPhone(bo.getContactMobile());
        dept.setStatus(EnableStatusEnum.Enabled);
        SysDept retDept =sysDeptData.add(dept);
        String deptId = retDept.getId();


        // 创建系统用户
        SysUser user = new SysUser();
        user.setTenantId(sysAgent.getTenantId());
        user.setAgentId(sysAgent.getId());
        user.setIsTenantAdmin(0);
        user.setIsAgentAdmin(1);
        user.setUserName(bo.getUserName());
        user.setNickName(bo.getUserName());
        user.setPassword(BCrypt.hashpw(bo.getPassword()));
        user.setDeptId(deptId);
        user.setUserType(AdminTypeEnum.AgentUser);
        user.setStatus(EnableStatusEnum.Enabled);

        user.setPhone(bo.getContactMobile());
        SysUser retUser=sysUserData.save(user);

        //新增系统用户后，默认当前用户为部门的负责人
        SysDept updateDept =sysDeptData.findById(retDept.getId());
        updateDept.setLeader(retUser.getUserName());
        sysDeptData.save(updateDept);

        // 用户和角色关联表
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(retUser.getId());
        userRole.setRoleId(roleId);

        sysUserRoleData.save(userRole);



        return true;
    }

    private String createAgentRole(SysAgent sysAgent) {

        // 获取套餐菜单id
        List<String> menuIds = sysAgent.getMenuIds();

        // 创建角色
        SysRole role = new SysRole();
        role.setTenantId(sysAgent.getTenantId());
        role.setAgentId(sysAgent.getId());
        role.setRoleName(sysAgent.getName());
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

    @Override
    @Transactional
    public boolean updateAgent(SysAgentBo bo) {
        SysAgent di = bo.to(SysAgent.class);
        sysAgentData.update(di);


        return true;
    }

    @Override
    public void updateStatus(SysAgentBo bo) {
        SysAgent sysAgent = sysAgentData.findById(bo.getId());
        sysAgent.setStatus(bo.getStatus());
        sysAgentData.save(sysAgent);
    }

    @Override
    @Transactional
    public void deleteAgent(String id) {
        SysAgent sysAgent = sysAgentData.findById(id);
        if (ObjectUtil.isNull(sysAgent)) {
            throw new BizException(ErrCode.SYS_AGENT_NOT_FOUND);
        }

        String agentId = sysAgent.getId();

        //检查是否存在合作商
        List<SysDealer> sysDealers = sysDealerData.findListByAgentId(agentId);
        if(!sysDealers.isEmpty()) {
            throw new BizException(ErrCode.SYS_AGENT_DELETE_ALREADY_NOT_EMPTY);
        }

        //删除角色
        List<SysRole> roles = sysRoleData.findAllByAgentId(agentId);

        //删除角色菜单
        sysRoleMenuData.deleteByRoleId(roles.stream().map(SysRole::getId).collect(Collectors.toList()));

        //删除角色
        sysRoleData.deleteByIds(roles.stream().map(SysRole::getId).collect(Collectors.toList()));

        //删除部门
        List<SysDept> depts = sysDeptData.findAllByAgentId(agentId);
        sysDeptData.deleteByIds(depts.stream().map(SysDept::getId).collect(Collectors.toList()));

        //删除系统用户
        List<SysUser> sysUsers = sysUserData.findAllByAgentId(agentId);
        for(SysUser sysUser: sysUsers) {
            //删除用户角色
            sysUserRoleData.deleteByUserId(sysUser.getId());
            //删除用户
            sysUserData.deleteById(sysUser.getId());
        }

        //删除系统登陆信息
        List<SysLoginInfo> sysLoginInfos = sysLogininforData.findAllByAgentId(agentId);
        sysLogininforData.deleteByIds(sysLoginInfos.stream().map(SysLoginInfo::getId).collect(Collectors.toList()));

        //删除代理绑定场站
        List<SysAgentStation> sysAgentStations = sysAgentStationData.findByAgentId(agentId);
        for(SysAgentStation sysAgentStation: sysAgentStations) {

            Station station = stationData.findById(sysAgentStation.getStationId());
            if(station == null) {
                throw new BizException(ErrCode.STATION_NOT_FOUND);
            }

            if(StringUtils.isNoneBlank(station.getAgentId()) && !station.getAgentId().equals(sysAgentStation.getAgentId())) {
                throw new BizException(ErrCode.SYS_AGENT_STATION_NOT_FOUND);
            }

            List<SysAgentStationDealer> sysAgentStationDealers = sysAgentStationDealerData.findByAgentStationId(sysAgentStation.getId());
            if(!sysAgentStationDealers.isEmpty()) {
                List<String> agentStationDealerIds = sysAgentStationDealers.stream().map(SysAgentStationDealer::getId).collect(Collectors.toList());
                sysAgentStationDealerData.deleteByIds(agentStationDealerIds);
            }
            sysAgentStationData.deleteById(sysAgentStation.getId());

            station.setAgentId(null);
            stationData.update(station);

        }

        //删除代理
        sysAgentData.deleteById(id);
    }

    @Override
    @Transactional
    public boolean batchDeleteAgent(List<String> ids) {
        for(String id: ids) {
            deleteAgent(id);
        }
        return true;
    }
}