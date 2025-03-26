package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.MapConfigRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IMapConfigData;
import com.obast.charer.data.model.TbMapConfig;
import com.obast.charer.enums.EnableStatusEnum;

import com.obast.charer.model.map.MapConfig;
import com.obast.charer.qo.MapConfigQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Primary
@Service
@RequiredArgsConstructor
public class MapConfigDataImpl extends AbstractCommonData<MapConfigQueryBo>
        implements IMapConfigData, IJPACommData<MapConfig, String>, IJPACommonData<MapConfig, MapConfigQueryBo, String> {

    private final MapConfigRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() { return baseRepository; }

    @Override
    public Class<?> getJpaRepositoryClass() { return TbMapConfig.class; }

    @Override
    public Class<?> getTClass() { return MapConfig.class;  }

    @Override
    public Paging<MapConfig> findPage(PageRequest<MapConfigQueryBo> request) {
        Specification<TbMapConfig> specification = buildSpecification(request.getData());
        Page<TbMapConfig> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbMapConfig> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, MapConfig.class));
    }

    @Override
    public List<MapConfig> findList(MapConfigQueryBo queryBo) {
        Specification<TbMapConfig> specification = buildSpecification(queryBo);
        List<TbMapConfig> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, MapConfig.class);
    }

    @Override
    public MapConfig findMapConfigByTenantId(String tenantId) {
        Specification<TbMapConfig> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode()));
            predicates.add(cb.equal(root.get("tenantId"), tenantId));

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbMapConfig> list = baseRepository.findAll(specification);
        if(list.isEmpty()) {
            return null;
        }
        return MapstructUtils.convert(list.get(0), MapConfig.class);
    }

    public Specification<TbMapConfig> buildSpecification(MapConfigQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getId())) {
                Predicate predicate = cb.equal(root.get("id"), bo.getId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getName())) {
                Predicate predicate = cb.equal(root.get("name"), bo.getName());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getIdentifier())) {
                Predicate statusPredicate = cb.equal(root.get("identifier"), bo.getIdentifier());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
