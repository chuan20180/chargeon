package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysMenuRepository;
import com.obast.charer.data.dao.SysTenantPackageRepository;
import com.obast.charer.data.model.TbSysMenu;
import com.obast.charer.data.model.TbSysTenantPackage;
import com.obast.charer.data.system.ISysTenantPackageData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysTenantPackage;
import com.obast.charer.qo.SysTenantPackageQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class SysTenantPackageDataImpl extends AbstractCommonData<SysTenantPackageQueryBo>
        implements ISysTenantPackageData, IJPACommData<SysTenantPackage, String>, IJPACommonData<SysTenantPackage, SysTenantPackageQueryBo, String> {

    @Autowired
    private SysTenantPackageRepository baseRepository;

    @Autowired
    private SysMenuRepository sysMenuRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysTenantPackage.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysTenantPackage.class;
    }

    @Override
    public Paging<SysTenantPackage> findPage(PageRequest<SysTenantPackageQueryBo> request) {
        Specification<TbSysTenantPackage> specification = buildSpecification(request.getData());
        Page<TbSysTenantPackage> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysTenantPackage> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysTenantPackage.class));
    }

    @Override
    public List<SysTenantPackage> findList(SysTenantPackageQueryBo queryBo) {
        Specification<TbSysTenantPackage> specification = buildSpecification(queryBo);
        List<TbSysTenantPackage> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysTenantPackage.class);
    }

    @Override
    public List<String> findMenuListByPackageId(String packageId) {
        TbSysTenantPackage tbSysTenantPackage = baseRepository.findById(packageId).orElse(null);
        if(tbSysTenantPackage == null) {
            return new ArrayList<>();
        }
        List<String> menuIds = tbSysTenantPackage.getMenuIds();

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

    public Specification<TbSysTenantPackage> buildSpecification(SysTenantPackageQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(bo != null) {
                if(bo.getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), bo.getStatus().getCode()));
                }
                if(StringUtils.isNoneBlank(bo.getName())) {
                    predicates.add(cb.equal(root.get("name"), bo.getName()));
                }
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public SysTenantPackage add(SysTenantPackage entity) {
        entity.setStatus(EnableStatusEnum.Enabled);
        TbSysTenantPackage tbSysTenantPackage = baseRepository.saveAndFlush(entity.to(TbSysTenantPackage.class));
        return MapstructUtils.convert(tbSysTenantPackage, SysTenantPackage.class);
    }

    @Override
    public SysTenantPackage update(SysTenantPackage entity) {
        TbSysTenantPackage tbSysTenantPackage = baseRepository.saveAndFlush(entity.to(TbSysTenantPackage.class));
        return MapstructUtils.convert(tbSysTenantPackage, SysTenantPackage.class);
    }
}
