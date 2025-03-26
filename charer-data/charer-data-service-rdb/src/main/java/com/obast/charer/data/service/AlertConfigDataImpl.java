package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IAlertConfigData;
import com.obast.charer.data.dao.AlertConfigRepository;
import com.obast.charer.data.model.TbAlertConfig;
import com.obast.charer.model.alert.AlertConfig;
import com.obast.charer.qo.AlertConfigQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AlertConfigDataImpl extends AbstractCommonData<AlertConfigQueryBo>
        implements IAlertConfigData, IJPACommData<AlertConfig, String>, IJPACommonData<AlertConfig, AlertConfigQueryBo, String> {

    @Autowired
    private AlertConfigRepository baseRepository;


    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbAlertConfig.class;
    }

    @Override
    public Class<?> getTClass() {
        return AlertConfig.class;
    }

    @Override
    public Paging<AlertConfig> findPage(PageRequest<AlertConfigQueryBo> request) {
        Specification<TbAlertConfig> specification = buildSpecification(request.getData());
        Page<TbAlertConfig> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbAlertConfig> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, AlertConfig.class));
    }

    @Override
    public List<AlertConfig> findList(AlertConfigQueryBo queryBo) {
        Specification<TbAlertConfig> specification = buildSpecification(queryBo);
        List<TbAlertConfig> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, AlertConfig.class);
    }

    public Specification<TbAlertConfig> buildSpecification(AlertConfigQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

}
