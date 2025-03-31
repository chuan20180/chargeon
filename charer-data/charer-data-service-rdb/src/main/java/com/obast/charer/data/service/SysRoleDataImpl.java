package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysRoleMenuRepository;
import com.obast.charer.data.dao.SysRoleRepository;
import com.obast.charer.data.dao.SysUserRoleRepository;
import com.obast.charer.data.model.TbSysRole;
import com.obast.charer.data.model.TbSysRoleMenu;
import com.obast.charer.data.model.TbSysUserRole;
import com.obast.charer.data.system.ISysRoleData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysRole;
import com.obast.charer.qo.SysRoleQueryBo;
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
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class SysRoleDataImpl extends AbstractCommonData<SysRoleQueryBo>
        implements ISysRoleData, IJPACommData<SysRole, String>, IJPACommonData<SysRole, SysRoleQueryBo, String> {

    private final SysRoleRepository baseRepository;

    private final SysUserRoleRepository sysUserRoleRepository;

    private final SysRoleMenuRepository sysRoleMenuRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysRole.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysRole.class;
    }


    @Override
    public Paging<SysRole> findPage(PageRequest<SysRoleQueryBo> request) {
        Specification<TbSysRole> specification = buildSpecification(request.getData());
        Page<TbSysRole> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysRole> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysRole.class));
    }

    @Override
    public List<SysRole> findList(SysRoleQueryBo queryBo) {
        Specification<TbSysRole> specification = buildSpecification(queryBo);
        List<TbSysRole> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysRole.class);
    }

    public Specification<TbSysRole> buildSpecification(SysRoleQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public List<SysRole> findListByUserId(String userId) {
        Specification<TbSysUserRole> specification = (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
            javax.persistence.criteria.Predicate predicate = cb.equal(root.get("userId"),userId);
            predicates.add(predicate);
            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
        List<TbSysUserRole> tbSysUserRoles = sysUserRoleRepository.findAll(specification);
        if(!tbSysUserRoles.isEmpty()) {
            List<String> roleIds = tbSysUserRoles.stream().map(TbSysUserRole::getRoleId).collect(Collectors.toList());
            Specification<TbSysRole> roleSpecification = (root, query, cb) -> {
                List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
                javax.persistence.criteria.Predicate predicate = root.get("id").in(roleIds);
                predicates.add(predicate);
                return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
            };
            return MapstructUtils.convert(baseRepository.findAll(roleSpecification), SysRole.class);
        }
        return new ArrayList<>();
    }

    @Transactional
    @Override
    public SysRole add(SysRole sysRole) {
        TbSysRole bo = sysRole.to(TbSysRole.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public SysRole update(SysRole sysRole) {
        TbSysRole bo = sysRole.to(TbSysRole.class);
        if (StringUtils.isBlank(bo.getId())) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private SysRole fillData(TbSysRole tbSysRole) {
        return MapstructUtils.convert(tbSysRole, SysRole.class);
    }



    @Override
    public List<String> selectMenuListByRoleId(String roleId, boolean menuCheckStrictly) {

        Specification<TbSysRoleMenu> spec = (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
            javax.persistence.criteria.Predicate predicate = cb.equal(root.get("roleId"),roleId);
            predicates.add(predicate);

            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
        List<TbSysRoleMenu> tbSysRoleMenus = sysRoleMenuRepository.findAll(spec);

        return tbSysRoleMenus.stream().map(TbSysRoleMenu::getId).collect(Collectors.toList());
    }


    @Override
    public boolean checkRoleNameUnique(SysRole role) {
        Specification<TbSysRole> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate1 = cb.equal(root.get("roleName"), role.getRoleName());
            predicates.add(predicate1);
            if(StringUtils.isNoneBlank(role.getId())) {
                Predicate predicate2 = cb.notEqual(root.get("id"), role.getId());
                predicates.add(predicate2);
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return baseRepository.count(specification) == 0;
    }

    @Override
    public boolean checkRoleKeyUnique(SysRole role) {
        Specification<TbSysRole> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate1 = cb.equal(root.get("roleKey"), role.getRoleKey());
            predicates.add(predicate1);
            if(StringUtils.isNoneBlank(role.getId())) {
                Predicate predicate2 = cb.notEqual(root.get("id"), role.getId());
                predicates.add(predicate2);
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return baseRepository.count(specification) == 0;
    }


    @Override
    public void deleteById(String id) {
        baseRepository.deleteById(id);
    }
}
