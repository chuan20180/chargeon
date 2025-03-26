package com.obast.charer.data.service;

import com.obast.charer.common.tenant.helper.TenantHelper;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.SysUserRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;

import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.model.TbStation;
import com.obast.charer.data.model.TbSysUser;
import com.obast.charer.data.system.ISysDeptData;
import com.obast.charer.data.system.ISysRoleData;
import com.obast.charer.data.system.ISysUserData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.station.Station;
import com.obast.charer.model.system.SysDept;
import com.obast.charer.model.system.SysRole;
import com.obast.charer.model.system.SysUser;
import com.obast.charer.qo.StationQueryBo;
import com.obast.charer.qo.SysUserQueryBo;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class SysUserDataImpl extends AbstractCommonData<SysUserQueryBo>
        implements ISysUserData, IJPACommData<SysUser, String>, IJPACommonData<SysUser, SysUserQueryBo, String> {

    private final SysUserRepository baseRepository;

    private final ISysDeptData sysDeptData;

    private final ISysRoleData sysRoleData;



    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysUser.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysUser.class;
    }

    @Override
    public Paging<SysUser> findPage(PageRequest<SysUserQueryBo> request) {
        Specification<TbSysUser> specification = buildSpecification(request.getData());
        Page<TbSysUser> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysUser> list = page.getContent();
        long total = page.getTotalElements();

        List<SysUser> newList = new ArrayList<>();
        for(TbSysUser tbStation: list) {
            newList.add(fillData(tbStation));
        }
        return new Paging<>(total, newList);
    }

    @Override
    public List<SysUser> findList(SysUserQueryBo queryBo) {
        Specification<TbSysUser> specification = buildSpecification(queryBo);
        List<TbSysUser> list = baseRepository.findAll(specification);
        List<SysUser> newList = new ArrayList<>();
        for(TbSysUser tbStation: list) {
            newList.add(fillData(tbStation));
        }
        return newList;
    }


    @Transactional
    @Override
    public SysUser add(SysUser sysUser) {
        TbSysUser bo = sysUser.to(TbSysUser.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public SysUser update(SysUser sysUser) {
        TbSysUser bo = sysUser.to(TbSysUser.class);
        if (StringUtils.isBlank(bo.getId())) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private SysUser fillData(TbSysUser tbSysUser) {
        SysUser vo =  MapstructUtils.convert(tbSysUser, SysUser.class);
        if(vo == null) {
            return null;
        }
        String userId = vo.getId();
        List<SysRole> sysRoles = sysRoleData.findListByUserId(userId);
        if(sysRoles != null && !sysRoles.isEmpty()) {
            vo.setRoleIds(sysRoles.stream().map(SysRole::getId).collect(Collectors.toList()));
        }

        return vo;
    }

    public Specification<TbSysUser> buildSpecification(SysUserQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public long countByDeptId(String aLong) {
        return 0;
    }

    @Override
    public boolean checkUserNameUnique(SysUser sysUser) {
        Specification<TbSysUser> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("userName"), sysUser.getUserName()));

            if(StringUtils.isNoneBlank(sysUser.getId())) {
                predicates.add(cb.notEqual(root.get("id"), sysUser.getId()));
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return baseRepository.count(specification) == 0;
    }

    @Override
    public SysUser findById(String userId) {
        Optional<TbSysUser> optUser = baseRepository.findById(userId);
        if (optUser.isEmpty()) {
            return null;
        }

        SysUser convert = MapstructUtils.convert(optUser.get(), SysUser.class);
        List<SysRole> sysRoles = sysRoleData.findListByUserId(userId);
        convert.setRoles(sysRoles);

        String deptId = convert.getDeptId();
        if (deptId == null) {
            return convert;
        }

        SysDept dept = sysDeptData.findById(deptId);
        if (ObjectUtil.isNotNull(dept)) {
            convert.setDept(dept);
        }
        return convert;
    }

    @Override
    public SysUser findTenantUserByUserName(String username, String tenantId) {

        Specification<TbSysUser> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate statusPredicate = cb.equal(root.get("userName"), username);
            predicates.add(statusPredicate);

            if(TenantHelper.isEnable()) {
                Predicate tenantIdPredicate = cb.equal(root.get("tenantId"), tenantId);
                predicates.add(tenantIdPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        TbSysUser sysUser = baseRepository.findOne(specification).orElse(null);

        return fillData(sysUser);
    }

    @Override
    public SysUser findByPhone(String phone) {
        Specification<TbSysUser> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("phone"), phone);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbSysUser sysUser = baseRepository.findOne(specification).orElse(null);
        return fillData(sysUser);
    }

    @Override
    public SysUser findByUserName(String userName) {
        Specification<TbSysUser> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("userName"), userName);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        TbSysUser sysUser = baseRepository.findOne(specification).orElse(null);

        return fillData(sysUser);
    }

    @Override
    public List<SysUser> findAllByTenantId(String id) {
        return MapstructUtils.convert(baseRepository.findByTenantId(id), SysUser.class);
    }

    @Override
    public List<SysUser> findAllByAgentId(String id) {
        return MapstructUtils.convert(baseRepository.findByAgentId(id), SysUser.class);
    }

    @Override
    public List<SysUser> findAllByDealerId(String id) {
        return MapstructUtils.convert(baseRepository.findByDealerId(id), SysUser.class);
    }


}
