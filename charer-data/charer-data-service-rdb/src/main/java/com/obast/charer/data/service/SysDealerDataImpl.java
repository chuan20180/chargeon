package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.SysDealerRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.model.TbSysDealer;
import com.obast.charer.data.system.ISysDealerData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysDealer;
import com.obast.charer.qo.SysDealerQueryBo;
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
public class SysDealerDataImpl extends AbstractCommonData<SysDealerQueryBo>
        implements ISysDealerData, IJPACommData<SysDealer, String>, IJPACommonData<SysDealer, SysDealerQueryBo, String> {

    private final SysDealerRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysDealer.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysDealer.class;
    }

    @Override
    public Paging<SysDealer> findPage(PageRequest<SysDealerQueryBo> request) {
        Specification<TbSysDealer> specification = buildSpecification(request.getData());
        Page<TbSysDealer> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysDealer> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysDealer.class));
    }

    @Override
    public List<SysDealer> findList(SysDealerQueryBo queryBo) {
        Specification<TbSysDealer> specification = buildSpecification(queryBo);
        List<TbSysDealer> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysDealer.class);
    }

    @Override
    public List<SysDealer> findListByAgentId(String agentId) {
        Specification<TbSysDealer> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("agentId"), agentId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysDealer> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysDealer.class);
    }

    @Transactional
    @Override
    public SysDealer add(SysDealer sysDealer) {
        TbSysDealer bo = sysDealer.to(TbSysDealer.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public SysDealer update(SysDealer sysDealer) {
        TbSysDealer bo = sysDealer.to(TbSysDealer.class);
        if (StringUtils.isBlank(bo.getId())) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private SysDealer fillData(TbSysDealer tbSysDealer) {
        return MapstructUtils.convert(tbSysDealer, SysDealer.class);
    }


    public Specification<TbSysDealer> buildSpecification(SysDealerQueryBo bo) {
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
