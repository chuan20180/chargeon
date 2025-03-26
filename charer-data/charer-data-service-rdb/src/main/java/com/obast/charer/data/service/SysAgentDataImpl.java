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
import com.obast.charer.data.dao.SysAgentRepository;
import com.obast.charer.data.model.TbSysAgent;
import com.obast.charer.data.system.ISysAgentData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysAgent;
import com.obast.charer.qo.SysAgentQueryBo;
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
public class SysAgentDataImpl extends AbstractCommonData<SysAgentQueryBo>
        implements ISysAgentData, IJPACommData<SysAgent, String>, IJPACommonData<SysAgent, SysAgentQueryBo, String> {

    private final SysAgentRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysAgent.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysAgent.class;
    }

    @Override
    public Paging<SysAgent> findPage(PageRequest<SysAgentQueryBo> request) {
        Specification<TbSysAgent> specification = buildSpecification(request.getData());
        Page<TbSysAgent> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysAgent> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysAgent.class));
    }

    @Override
    public List<SysAgent> findList(SysAgentQueryBo queryBo) {
        Specification<TbSysAgent> specification = buildSpecification(queryBo);
        List<TbSysAgent> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysAgent.class);
    }

    @Override
    public List<SysAgent> findListByTenantId(String tenantId) {
        Specification<TbSysAgent> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("tenantId"), tenantId);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        return MapstructUtils.convert(baseRepository.findAll(specification), SysAgent.class);
    }


    @Transactional
    @Override
    public SysAgent add(SysAgent sysAgent) {
        TbSysAgent bo = sysAgent.to(TbSysAgent.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public SysAgent update(SysAgent sysAgent) {
        TbSysAgent bo = sysAgent.to(TbSysAgent.class);
        if (StringUtils.isBlank(bo.getId())) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private SysAgent fillData(TbSysAgent tbSysAgent) {
        return MapstructUtils.convert(tbSysAgent, SysAgent.class);
    }


    public Specification<TbSysAgent> buildSpecification(SysAgentQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if(StringUtils.isNoneBlank(bo.getName())) {
                Predicate predicate = cb.equal(root.get("name"), bo.getName());
                predicates.add(predicate);
            }


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
