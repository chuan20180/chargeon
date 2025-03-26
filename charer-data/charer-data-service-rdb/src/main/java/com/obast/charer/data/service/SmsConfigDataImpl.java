package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SmsConfigRepository;
import com.obast.charer.data.business.ISmsConfigData;
import com.obast.charer.data.model.TbSmsConfig;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.sms.SmsConfig;
import com.obast.charer.qo.SmsConfigQueryBo;
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
public class SmsConfigDataImpl extends AbstractCommonData<SmsConfigQueryBo>
        implements ISmsConfigData, IJPACommData<SmsConfig, String>, IJPACommonData<SmsConfig, SmsConfigQueryBo, String> {

    private final SmsConfigRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSmsConfig.class;
    }

    @Override
    public Class<?> getTClass() {
        return SmsConfig.class;
    }

    @Override
    public Paging<SmsConfig> findPage(PageRequest<SmsConfigQueryBo> request) {
        Specification<TbSmsConfig> specification = buildSpecification(request.getData());
        Page<TbSmsConfig> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSmsConfig> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SmsConfig.class));
    }

    @Override
    public List<SmsConfig> findList(SmsConfigQueryBo queryBo) {
        Specification<TbSmsConfig> specification = buildSpecification(queryBo);
        List<TbSmsConfig> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SmsConfig.class);
    }

    @Override
    public SmsConfig findAvaiableSmsConfig() {
        Specification<TbSmsConfig> specification = (root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
            return query.where(predicate).getRestriction();
        };
        List<TbSmsConfig> list = baseRepository.findAll(specification);
        if(list.isEmpty()) {
            return null;
        }
        return MapstructUtils.convert(list.get(0), SmsConfig.class);
    }

    public Specification<TbSmsConfig> buildSpecification(SmsConfigQueryBo bo) {
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
