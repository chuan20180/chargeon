package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysOperLogRepository;
import com.obast.charer.data.model.TbSysOperLog;
import com.obast.charer.data.system.ISysOperLogData;
import com.obast.charer.model.system.SysOperLog;
import com.obast.charer.qo.SysOperLogQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class SysOperLogDataImpl  extends AbstractCommonData<SysOperLogQueryBo>
        implements ISysOperLogData, IJPACommData<SysOperLog, String>, IJPACommonData<SysOperLog, SysOperLogQueryBo, String> {

    private final SysOperLogRepository baseRepository;


    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysOperLog.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysOperLog.class;
    }


    @Override
    public Paging<SysOperLog> findPage(PageRequest<SysOperLogQueryBo> request) {
        Specification<TbSysOperLog> specification = buildSpecification(request.getData());
        Page<TbSysOperLog> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysOperLog> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysOperLog.class));
    }

    @Override
    public List<SysOperLog> findList(SysOperLogQueryBo queryBo) {
        Specification<TbSysOperLog> specification = buildSpecification(queryBo);
        List<TbSysOperLog> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysOperLog.class);
    }

    public Specification<TbSysOperLog> buildSpecification(SysOperLogQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
    }

}
