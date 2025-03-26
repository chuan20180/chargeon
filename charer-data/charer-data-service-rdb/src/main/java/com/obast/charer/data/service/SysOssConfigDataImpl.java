package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysOssConfigRepository;
import com.obast.charer.data.model.TbSysOssConfig;
import com.obast.charer.data.system.ISysOssConfigData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysOssConfig;
import com.obast.charer.qo.SysOssConfigQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class SysOssConfigDataImpl extends AbstractCommonData<SysOssConfigQueryBo>
        implements ISysOssConfigData, IJPACommData<SysOssConfig, String>, IJPACommonData<SysOssConfig, SysOssConfigQueryBo, String> {

    private final SysOssConfigRepository baseRepository;


    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysOssConfig.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysOssConfig.class;
    }


    @Override
    public Paging<SysOssConfig> findPage(PageRequest<SysOssConfigQueryBo> request) {
        Specification<TbSysOssConfig> specification = buildSpecification(request.getData());
        Page<TbSysOssConfig> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysOssConfig> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysOssConfig.class));
    }

    @Override
    public List<SysOssConfig> findList(SysOssConfigQueryBo queryBo) {
        Specification<TbSysOssConfig> specification = buildSpecification(queryBo);
        List<TbSysOssConfig> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysOssConfig.class);
    }

    @Override
    public SysOssConfig findByConfigKey(String configKey) {
        Specification<TbSysOssConfig> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("configKey"), configKey);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbSysOssConfig entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, SysOssConfig.class);
    }


    @Transactional
    @Override
    public SysOssConfig add(SysOssConfig sysOssConfig) {
        TbSysOssConfig bo = sysOssConfig.to(TbSysOssConfig.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public SysOssConfig update(SysOssConfig sysOssConfig) {
        TbSysOssConfig bo = sysOssConfig.to(TbSysOssConfig.class);
        if (StringUtils.isBlank(bo.getId())) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private SysOssConfig fillData(TbSysOssConfig tbSysOssConfig) {
        return MapstructUtils.convert(tbSysOssConfig, SysOssConfig.class);
    }


    public Specification<TbSysOssConfig> buildSpecification(SysOssConfigQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
