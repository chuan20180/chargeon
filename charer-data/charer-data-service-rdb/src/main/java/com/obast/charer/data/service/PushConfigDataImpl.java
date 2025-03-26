package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.PushConfigRepository;
import com.obast.charer.data.business.IPushConfigData;
import com.obast.charer.data.model.TbPushConfig;
import com.obast.charer.enums.EnableStatusEnum;

import com.obast.charer.model.push.PushConfig;
import com.obast.charer.qo.PushConfigQueryBo;
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
public class PushConfigDataImpl extends AbstractCommonData<PushConfigQueryBo>
        implements IPushConfigData, IJPACommData<PushConfig, String>, IJPACommonData<PushConfig, PushConfigQueryBo, String> {

    private final PushConfigRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbPushConfig.class;
    }

    @Override
    public Class<?> getTClass() {
        return PushConfig.class;
    }

    @Override
    public Paging<PushConfig> findPage(PageRequest<PushConfigQueryBo> request) {
        Specification<TbPushConfig> specification = buildSpecification(request.getData());
        Page<TbPushConfig> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbPushConfig> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, PushConfig.class));
    }

    @Override
    public List<PushConfig> findList(PushConfigQueryBo queryBo) {
        Specification<TbPushConfig> specification = buildSpecification(queryBo);
        List<TbPushConfig> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, PushConfig.class);
    }

    @Override
    public PushConfig findAvailableConfig() {
        Specification<TbPushConfig> specification = (root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
            return query.where(predicate).getRestriction();
        };
        List<TbPushConfig> list = baseRepository.findAll(specification);
        if(list.isEmpty()) {
            return null;
        }
        return MapstructUtils.convert(list.get(0), PushConfig.class);
    }

    @Override
    public PushConfig findByIdentifier(String identifier) {
        return MapstructUtils.convert(baseRepository.findByIdentifier(identifier), PushConfig.class);
    }

    public Specification<TbPushConfig> buildSpecification(PushConfigQueryBo bo) {
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
