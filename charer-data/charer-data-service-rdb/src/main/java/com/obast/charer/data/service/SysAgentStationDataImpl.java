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
import com.obast.charer.data.dao.SysAgentRepository;
import com.obast.charer.data.dao.SysAgentStationRepository;
import com.obast.charer.data.model.TbSysAgentStation;
import com.obast.charer.data.system.ISysAgentStationData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysAgent;
import com.obast.charer.model.system.SysAgentStation;
import com.obast.charer.qo.SysAgentStationQueryBo;
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
public class SysAgentStationDataImpl extends AbstractCommonData<SysAgentStationQueryBo>
        implements ISysAgentStationData, IJPACommData<SysAgentStation, String>, IJPACommonData<SysAgentStation, SysAgentStationQueryBo, String> {

    private final SysAgentStationRepository baseRepository;

    private final SysAgentRepository sysAgentRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysAgentStation.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysAgentStation.class;
    }

    @Override
    public Paging<SysAgentStation> findPage(PageRequest<SysAgentStationQueryBo> request) {
        Specification<TbSysAgentStation> specification = buildSpecification(request.getData());
        Page<TbSysAgentStation> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysAgentStation> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysAgentStation.class));
    }

    @Override
    public List<SysAgentStation> findList(SysAgentStationQueryBo queryBo) {
        Specification<TbSysAgentStation> specification = buildSpecification(queryBo);
        List<TbSysAgentStation> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysAgentStation.class);
    }


    @Override
    public List<SysAgentStation> findByAgentId(String agentId) {
        Specification<TbSysAgentStation> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate predicate = cb.equal(root.get("agentId"), agentId);
            predicates.add(predicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysAgentStation> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysAgentStation.class);
    }


    @Override
    public SysAgent findAgentByStationId(String stationId) {
        Specification<TbSysAgentStation> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("stationId"), stationId);
            predicates.add(predicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        List<TbSysAgentStation> list = baseRepository.findAll(specification);

        TbSysAgentStation sysAgentStation = null;
        if(list.size() == 1) {
            sysAgentStation = list.get(0);
        }

        if(sysAgentStation == null) {
            return null;
        }

        return MapstructUtils.convert(sysAgentRepository.findById(sysAgentStation.getAgentId()).orElse(null), SysAgent.class);
    }


    @Transactional
    @Override
    public SysAgentStation add(SysAgentStation sysAgent) {
        TbSysAgentStation bo = sysAgent.to(TbSysAgentStation.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public SysAgentStation update(SysAgentStation sysAgent) {
        TbSysAgentStation bo = sysAgent.to(TbSysAgentStation.class);
        if (StringUtils.isBlank(bo.getId())) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private SysAgentStation fillData(TbSysAgentStation tbSysAgent) {
        return MapstructUtils.convert(tbSysAgent, SysAgentStation.class);
    }


    public Specification<TbSysAgentStation> buildSpecification(SysAgentStationQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if(StringUtils.isNoneBlank(bo.getAgentId())) {
                Predicate predicate = cb.equal(root.get("agentId"), bo.getAgentId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getStationId())) {
                Predicate predicate = cb.equal(root.get("stationId"), bo.getStationId());
                predicates.add(predicate);
            }


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
