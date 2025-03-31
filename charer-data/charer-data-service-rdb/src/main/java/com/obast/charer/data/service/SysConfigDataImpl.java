package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysConfigRepository;
import com.obast.charer.data.model.TbSysConfig;
import com.obast.charer.data.system.ISysConfigData;
import com.obast.charer.model.system.SysConfig;
import com.obast.charer.qo.SysConfigQueryBo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


@Primary
@Service
@RequiredArgsConstructor
public class SysConfigDataImpl extends AbstractCommonData<SysConfigQueryBo>
        implements ISysConfigData, IJPACommData<SysConfig, String>, IJPACommonData<SysConfig, SysConfigQueryBo, String> {

    private final SysConfigRepository baseRepository;



    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysConfig.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysConfig.class;
    }

    @Override
    public Paging<SysConfig> findPage(PageRequest<SysConfigQueryBo> request) {
        Specification<TbSysConfig> specification = buildSpecification(request.getData());
        Page<TbSysConfig> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysConfig> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysConfig.class));
    }

    @Override
    public List<SysConfig> findList(SysConfigQueryBo queryBo) {
        Specification<TbSysConfig> specification = buildSpecification(queryBo);
        List<TbSysConfig> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysConfig.class);
    }

    @Override
    public SysConfig findByConfigKey(String configKey) {
        SysConfigQueryBo bo = new SysConfigQueryBo();
        bo.setConfigKey(configKey);
        Specification<TbSysConfig> specification = buildSpecification(bo);
        TbSysConfig entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, SysConfig.class);
    }

    @Override
    public SysConfig findById(String id) {
        TbSysConfig tbSysConfig = baseRepository.findById(id).orElseThrow(() ->
                new BizException(ErrCode.DATA_NOT_EXIST));
        return MapstructUtils.convert(tbSysConfig, SysConfig.class);
    }

    @Override
    public SysConfig save(SysConfig data) {
        baseRepository.save(data.to(TbSysConfig.class));
        return data;
    }

    @Override
    public List<SysConfig> findByIds(Collection<String> id) {
        Iterable<TbSysConfig> allById = baseRepository.findAllById(id);
        Iterator<TbSysConfig> iterator = allById.iterator();
        return MapstructUtils.convert(IteratorUtils.toList(iterator), SysConfig.class);
    }

    public Specification<TbSysConfig> buildSpecification(SysConfigQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getConfigKey())) {
                javax.persistence.criteria.Predicate predicate = cb.equal(root.get("configKey"), bo.getConfigKey());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getConfigName())) {
                javax.persistence.criteria.Predicate predicate = cb.equal(root.get("configName"), bo.getConfigName());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getConfigType())) {
                javax.persistence.criteria.Predicate predicate = cb.equal(root.get("configType"), bo.getConfigType());
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
    }
}
