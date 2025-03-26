package com.obast.charer.data.service;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.RechargeItemRepository;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.qo.RechargeQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.RechargeRepository;
import com.obast.charer.data.business.IRechargeData;
import com.obast.charer.data.model.TbRecharge;
import com.obast.charer.data.model.TbRechargeItem;
import com.obast.charer.model.platform.Recharge;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class RechargeDataImpl extends AbstractCommonData<RechargeQueryBo>
        implements IRechargeData, IJPACommData<Recharge, String>, IJPACommonData<Recharge, RechargeQueryBo, String> {

    private final RechargeRepository baseRepository;

    private final RechargeItemRepository rechargeItemRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbRecharge.class;
    }

    @Override
    public Class<?> getTClass() {
        return Recharge.class;
    }

    @Override
    public Paging<Recharge> findPage(PageRequest<RechargeQueryBo> request) {
        Specification<TbRecharge> specification = buildSpecification(request.getData());
        Page<TbRecharge> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbRecharge> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Recharge.class));
    }

    @Override
    public List<Recharge> findList(RechargeQueryBo queryBo) {
        Specification<TbRecharge> specification = buildSpecification(queryBo);
        List<TbRecharge> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Recharge.class);
    }

    @Override
    public Recharge findByName(String name) {
        RechargeQueryBo bo = new RechargeQueryBo();
        bo.setName(name);
        Specification<TbRecharge> specification = buildSpecification(bo);
        TbRecharge entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, Recharge.class);
    }

    @Transactional
    @Override
    public Recharge add(Recharge entity) {
        TbRecharge bo = entity.to(TbRecharge.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Recharge.class);
    }

    @Transactional
    @Override
    public Recharge update(Recharge entity) {
        TbRecharge bo = baseRepository.findById(entity.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(entity, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Recharge.class);
    }

    @Transactional
    @Override
    public void deleteById(String rechargeId) {
        baseRepository.deleteById(rechargeId);
        List<TbRechargeItem> rechargeItems = rechargeItemRepository.findByRechargeId(rechargeId);
        if(!rechargeItems.isEmpty()) {
            List<String> itemIds = rechargeItems.stream().map(TbRechargeItem::getId).collect(Collectors.toList());
            rechargeItemRepository.deleteAllById(itemIds);
        }
    }

    public Specification<TbRecharge> buildSpecification(RechargeQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getId())) {
                Predicate predicate = cb.equal(root.get("id"), bo.getId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getName())) {
                Predicate predicate = cb.like(root.get("name"), bo.getName());
                predicates.add(predicate);
            }

            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("type"), bo.getType().getCode());
                predicates.add(predicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}