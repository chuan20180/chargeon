package com.obast.charer.data.service;

import cn.hutool.core.util.ObjectUtil;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysMenuRepository;
import com.obast.charer.data.dao.SysRoleMenuRepository;
import com.obast.charer.data.dao.SysRoleRepository;
import com.obast.charer.data.model.TbSysMenu;
import com.obast.charer.data.model.TbSysRole;
import com.obast.charer.data.model.TbSysRoleMenu;
import com.obast.charer.data.system.ISysRoleMenuData;
import com.obast.charer.model.system.SysRoleMenu;
import com.obast.charer.qo.SysRoleMenuQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class SysRoleMenuDataImpl extends AbstractCommonData<SysRoleMenuQueryBo>
        implements ISysRoleMenuData, IJPACommData<SysRoleMenu, String>, IJPACommonData<SysRoleMenu, SysRoleMenuQueryBo, String> {


    private final SysRoleMenuRepository baseRepository;

    private final SysRoleRepository sysRoleRepository;

    private final SysMenuRepository sysMenuRepository;


    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysRoleMenu.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysRoleMenu.class;
    }


    @Override
    public Paging<SysRoleMenu> findPage(PageRequest<SysRoleMenuQueryBo> request) {
        Specification<TbSysRoleMenu> specification = buildSpecification(request.getData());
        Page<TbSysRoleMenu> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysRoleMenu> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysRoleMenu.class));
    }

    @Override
    public List<SysRoleMenu> findList(SysRoleMenuQueryBo queryBo) {
        Specification<TbSysRoleMenu> specification = buildSpecification(queryBo);
        return MapstructUtils.convert(baseRepository.findAll(specification), SysRoleMenu.class);
    }

    @Override
    public List<SysRoleMenu> findListByRoleId(String roleId) {
        Specification<TbSysRoleMenu> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("roleId"), roleId);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        return MapstructUtils.convert(baseRepository.findAll(specification), SysRoleMenu.class);
    }

    @Override
    public List<String> findMenuListByRoleId(String roleId) {
        TbSysRole tbSysRole = sysRoleRepository.findById(roleId).orElse(null);
        if(tbSysRole == null) {
            return new ArrayList<>();
        }

        Specification<TbSysRoleMenu> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("roleId"), roleId);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };


        List<TbSysRoleMenu> tbSysRoleMenus = baseRepository.findAll(specification);

        if(tbSysRoleMenus.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> menuIds = tbSysRoleMenus.stream().map(TbSysRoleMenu::getMenuId).collect(Collectors.toList());

        Specification<TbSysMenu> parentMenuSpecification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = root.get("id").in(menuIds);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysMenu> parentMenus = sysMenuRepository.findAll(parentMenuSpecification);

        List<String> parentMenuIds = parentMenus.stream().map(TbSysMenu::getParentId).collect(Collectors.toList());

        Specification<TbSysMenu> menuSpecification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = root.get("id").in(menuIds);
            predicates.add(predicate);

            Predicate predicate2 = root.get("id").in(parentMenuIds).not();
            predicates.add(predicate2);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysMenu> sysMenus = sysMenuRepository.findAll(menuSpecification);

        return sysMenus.stream().map(TbSysMenu::getId).collect(Collectors.toList());
    }


    public Specification<TbSysRoleMenu> buildSpecification(SysRoleMenuQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getMenuId())) {
                Predicate statusPredicate = cb.equal(root.get("menuId"), bo.getMenuId());
                predicates.add(statusPredicate);
            }

            if(StringUtils.isNoneBlank(bo.getRoleId())) {
                Predicate statusPredicate = cb.equal(root.get("roleId"), bo.getRoleId());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public boolean checkMenuExistRole(String menuId) {

        Specification<TbSysRoleMenu> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("menuId"), menuId);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        return baseRepository.count(specification) > 0;
    }

    @Override
    public long insertBatch(List<SysRoleMenu> list) {
        List<TbSysRoleMenu> tbSysRoleMenus = Objects.requireNonNull(MapstructUtils.convert(list, TbSysRoleMenu.class));
        return baseRepository.saveAll(tbSysRoleMenus).size();
    }

    @Override
    public void deleteByRoleId(Collection<String> roleIds) {
        Specification<TbSysRoleMenu> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(root.get("roleId").in(roleIds));
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        List<TbSysRoleMenu> sysRoleMenus = baseRepository.findAll(specification);
        if(ObjectUtil.isNotEmpty(sysRoleMenus)) {
             baseRepository.deleteAllById(sysRoleMenus.stream().map(TbSysRoleMenu::getId).collect(Collectors.toList()));
        }
    }
}
