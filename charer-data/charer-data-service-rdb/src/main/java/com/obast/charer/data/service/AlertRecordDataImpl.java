package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.AlertRecordRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IAlertRecordData;
import com.obast.charer.data.model.TbAlertRecord;
import com.obast.charer.model.alert.AlertRecord;
import com.obast.charer.qo.AlertRecordQueryBo;
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
public class AlertRecordDataImpl extends AbstractCommonData<AlertRecordQueryBo>
        implements IAlertRecordData, IJPACommData<AlertRecord, String>, IJPACommonData<AlertRecord, AlertRecordQueryBo, String> {

    @Autowired
    private AlertRecordRepository baseRepository;


    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbAlertRecord.class;
    }

    @Override
    public Class<?> getTClass() {
        return AlertRecord.class;
    }

    @Override
    public Paging<AlertRecord> findPage(PageRequest<AlertRecordQueryBo> request) {
        Specification<TbAlertRecord> specification = buildSpecification(request.getData());
        Page<TbAlertRecord> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbAlertRecord> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, AlertRecord.class));
    }

    @Override
    public List<AlertRecord> findList(AlertRecordQueryBo queryBo) {
        Specification<TbAlertRecord> specification = buildSpecification(queryBo);
        List<TbAlertRecord> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, AlertRecord.class);
    }

    public Specification<TbAlertRecord> buildSpecification(AlertRecordQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }


}
