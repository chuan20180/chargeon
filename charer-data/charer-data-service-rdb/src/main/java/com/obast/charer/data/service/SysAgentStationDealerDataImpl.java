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
import com.obast.charer.data.dao.SysAgentStationDealerRepository;
import com.obast.charer.data.model.TbSysAgentStationDealer;
import com.obast.charer.data.system.ISysAgentStationDealerData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysAgentStationDealer;
import com.obast.charer.qo.SysAgentStationDealerQueryBo;
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
public class SysAgentStationDealerDataImpl extends AbstractCommonData<SysAgentStationDealerQueryBo>
        implements ISysAgentStationDealerData, IJPACommData<SysAgentStationDealer, String>, IJPACommonData<SysAgentStationDealer, SysAgentStationDealerQueryBo, String> {

    private final SysAgentStationDealerRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysAgentStationDealer.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysAgentStationDealer.class;
    }

    @Override
    public Paging<SysAgentStationDealer> findPage(PageRequest<SysAgentStationDealerQueryBo> request) {
        Specification<TbSysAgentStationDealer> specification = buildSpecification(request.getData());
        Page<TbSysAgentStationDealer> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysAgentStationDealer> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysAgentStationDealer.class));
    }

    @Override
    public List<SysAgentStationDealer> findList(SysAgentStationDealerQueryBo queryBo) {
        Specification<TbSysAgentStationDealer> specification = buildSpecification(queryBo);
        List<TbSysAgentStationDealer> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysAgentStationDealer.class);
    }

    @Override
    public List<SysAgentStationDealer> findByAgentId(String agentId) {
        Specification<TbSysAgentStationDealer> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate predicate = cb.equal(root.get("agentId"), agentId);
            predicates.add(predicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysAgentStationDealer> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysAgentStationDealer.class);
    }

    @Override
    public List<SysAgentStationDealer> findByAgentStationId(String agentStationId) {
        Specification<TbSysAgentStationDealer> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate predicate = cb.equal(root.get("agentStationId"), agentStationId);
            predicates.add(predicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysAgentStationDealer> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysAgentStationDealer.class);
    }

    @Override
    public List<SysAgentStationDealer> findByDealerId(String dealerId) {
        Specification<TbSysAgentStationDealer> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate predicate = cb.equal(root.get("dealerId"), dealerId);
            predicates.add(predicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysAgentStationDealer> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysAgentStationDealer.class);
    }


    @Transactional
    @Override
    public SysAgentStationDealer add(SysAgentStationDealer sysAgentStationDealer) {
        TbSysAgentStationDealer bo = sysAgentStationDealer.to(TbSysAgentStationDealer.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public SysAgentStationDealer update(SysAgentStationDealer sysAgentStationDealer) {
        TbSysAgentStationDealer bo = sysAgentStationDealer.to(TbSysAgentStationDealer.class);
        if (StringUtils.isBlank(bo.getId())) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private SysAgentStationDealer fillData(TbSysAgentStationDealer tbSysAgentStationDealer) {
        return MapstructUtils.convert(tbSysAgentStationDealer, SysAgentStationDealer.class);
    }

    public Specification<TbSysAgentStationDealer> buildSpecification(SysAgentStationDealerQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getAgentId())) {
                Predicate predicate = cb.equal(root.get("agentId"), bo.getAgentId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getAgentStationId())) {
                Predicate predicate = cb.equal(root.get("agentStationId"), bo.getAgentStationId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getDealerId())) {
                Predicate predicate = cb.equal(root.get("dealerId"), bo.getDealerId());
                predicates.add(predicate);
            }

            if(bo.getStatus() != null) {
                Predicate predicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(predicate);
            }


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
