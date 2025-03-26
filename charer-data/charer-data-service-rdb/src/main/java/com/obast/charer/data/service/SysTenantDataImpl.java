package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysTenantRepository;
import com.obast.charer.data.model.TbSysTenant;
import com.obast.charer.data.system.ISysTenantData;
import com.obast.charer.model.system.SysTenant;
import com.obast.charer.qo.SysTenantQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class SysTenantDataImpl extends AbstractCommonData<SysTenantQueryBo>
        implements ISysTenantData, IJPACommData<SysTenant, String>, IJPACommonData<SysTenant, SysTenantQueryBo, String> {

    private final SysTenantRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() { return baseRepository; }

    @Override
    public Class<?> getJpaRepositoryClass() { return TbSysTenant.class; }

    @Override
    public Class<?> getTClass() {
        return SysTenant.class;
    }


    @Override
    public Paging<SysTenant> findPage(PageRequest<SysTenantQueryBo> request) {
        Specification<TbSysTenant> specification = buildSpecification(request.getData());
        Page<TbSysTenant> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysTenant> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysTenant.class));
    }

    @Override
    public List<SysTenant> findList(SysTenantQueryBo queryBo) {
        Specification<TbSysTenant> specification = buildSpecification(queryBo);
        List<TbSysTenant> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysTenant.class);
    }

    @Override
    public List<SysTenant> findListByPackageId(String packageId) {
        Specification<TbSysTenant> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate1 = cb.equal(root.get("packageId"), packageId);
            predicates.add(predicate1);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysTenant> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysTenant.class);
    }

    public Specification<TbSysTenant> buildSpecification(SysTenantQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getKeyword())) {
                String keyword = bo.getKeyword();
                Predicate predicateOr1 = cb.like(root.get("companyName").as(String.class), "%" + keyword + "%");
                Predicate predicateOr2 = cb.like(root.get("contactUserName").as(String.class), "%" + keyword + "%");
                Predicate predicateOr3 = cb.like(root.get("contactPhone").as(String.class), "%" + keyword + "%");
                Predicate predicateOr = cb.or(predicateOr1, predicateOr2, predicateOr3);
                predicates.add(predicateOr);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public boolean checkCompanyNameUnique(SysTenant tenant) {
        Specification<TbSysTenant> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate1 = cb.equal(root.get("companyName"), tenant.getCompanyName());
            predicates.add(predicate1);
            if(StringUtils.isNoneBlank(tenant.getId())) {
                Predicate predicate2 = cb.notEqual(root.get("id"), tenant.getId());
                predicates.add(predicate2);
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return baseRepository.count(specification) == 0;
    }

    @Override
    public SysTenant add(SysTenant entity) {
        String id = generateTenantId();
        entity.setId(id);
        entity.setTenantId(id);
        TbSysTenant tbSysTenant = baseRepository.saveAndFlush(MapstructUtils.convert(entity, TbSysTenant.class));
        return MapstructUtils.convert(tbSysTenant, SysTenant.class);
    }

    @Override
    public SysTenant update(SysTenant entity) {
        TbSysTenant tbSysTenant = baseRepository.saveAndFlush(MapstructUtils.convert(entity, TbSysTenant.class));
        return MapstructUtils.convert(tbSysTenant, SysTenant.class);
    }

    private String generateTenantId() {
        String maxTenantId = TenantConstants.DEFAULT_TENANT_ID;
        Sort sort = Sort.by("id").descending();
        List<TbSysTenant> list = baseRepository.findAll(sort);
        if(!list.isEmpty()) {
            SysTenant maxTenant = MapstructUtils.convert(list.get(0), SysTenant.class);
            maxTenantId = maxTenant.getId();
        }
        return String.valueOf(Integer.parseInt(maxTenantId) +1);
    }
}
