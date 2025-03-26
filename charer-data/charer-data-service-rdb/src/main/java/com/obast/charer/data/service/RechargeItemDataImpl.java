package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.RechargeItemRepository;
import com.obast.charer.qo.RechargeItemQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.platform.IRechargeItemData;
import com.obast.charer.data.model.TbRechargeItem;
import com.obast.charer.model.platform.RechargeItem;
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


@Primary
@Service
@RequiredArgsConstructor
public class RechargeItemDataImpl extends AbstractCommonData<RechargeItemQueryBo>
        implements IRechargeItemData, IJPACommData<RechargeItem, String>, IJPACommonData<RechargeItem, RechargeItemQueryBo, String> {

    private final RechargeItemRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbRechargeItem.class;
    }

    @Override
    public Class<?> getTClass() {
        return RechargeItem.class;
    }

    @Override
    public Paging<RechargeItem> findPage(PageRequest<RechargeItemQueryBo> request) {
        Specification<TbRechargeItem> specification = buildSpecification(request.getData());
        Page<TbRechargeItem> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbRechargeItem> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, RechargeItem.class));
    }

    @Override
    public List<RechargeItem> findList(RechargeItemQueryBo queryBo) {
        Specification<TbRechargeItem> specification = buildSpecification(queryBo);
        List<TbRechargeItem> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, RechargeItem.class);
    }

    @Override
    public List<RechargeItem> findByRechargeId(String rechargeId) {
        RechargeItemQueryBo bo = new RechargeItemQueryBo();
        bo.setRechargeId(rechargeId);
        Specification<TbRechargeItem> specification = buildSpecification(bo);
        List<TbRechargeItem> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, RechargeItem.class);
    }

    @Transactional
    @Override
    public void deleteById(String rechargeId) {
        baseRepository.deleteById(rechargeId);
    }

    public Specification<TbRechargeItem> buildSpecification(RechargeItemQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getId())) {
                Predicate predicate = cb.equal(root.get("id"), bo.getId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getRechargeId())) {
                Predicate predicate = cb.equal(root.get("rechargeId"), bo.getRechargeId());
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