package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IInstantData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.dao.InstantRepository;
import com.obast.charer.data.model.TbInstant;
import com.obast.charer.enums.LockEnum;

import com.obast.charer.enums.InstantStateEnum;

import com.obast.charer.model.Instant;
import com.obast.charer.qo.InstantQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class InstantDataImpl extends AbstractCommonData<InstantQueryBo>
        implements IInstantData, IJPACommData<Instant, String>, IJPACommonData<Instant, InstantQueryBo, String> {

    private final InstantRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbInstant.class;
    }

    @Override
    public Class<?> getTClass() {
        return Instant.class;
    }

    @Override
    public Instant findById(String id) {
        return MapstructUtils.convert(baseRepository.findById(id).orElse(null), Instant.class);
    }

    @Override
    public Paging<Instant> findPage(PageRequest<InstantQueryBo> request) {
        Specification<TbInstant> specification = buildSpecification(request.getData());
        Page<TbInstant> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbInstant> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Instant.class));
    }

    @Override
    public List<Instant> findList(InstantQueryBo queryBo) {
        Specification<TbInstant> specification = buildSpecification(queryBo);
        List<TbInstant> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Instant.class);
    }

    @Override
    public Instant findByTranId(String tranId) {
        Specification<TbInstant> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("tranId"), tranId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbInstant entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, Instant.class);
    }

    /*
     * 生成订单
     */
    @Override
    public Instant add(Instant instant) {
        instant.setTranId(this.generateTranId());
        TbInstant tbInstant = MapstructUtils.convert(instant, TbInstant.class);
        if(tbInstant == null) {
            return null;
        }
        return MapstructUtils.convert(baseRepository.saveAndFlush(tbInstant), Instant.class);
    }

    @Transactional
    @Override
    public Instant update(Instant entity) {
        TbInstant bo = baseRepository.findById(entity.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(entity, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Instant.class);
    }

    public Specification<TbInstant> buildSpecification(InstantQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getId())) {
                Predicate predicate = cb.equal(root.get("id"), bo.getId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getUserName())) {
                Predicate predicate = cb.equal(root.get("userName"), bo.getUserName());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getTranId())) {
                Predicate predicate = cb.equal(root.get("tranId"), bo.getTranId());
                predicates.add(predicate);
            }

            if(bo.getRechargeType() != null) {
                Predicate statusPredicate = cb.equal(root.get("rechargeType"), bo.getRechargeType().getCode());
                predicates.add(statusPredicate);
            }


            if(StringUtils.isNoneBlank(bo.getPaymentName())) {
                Predicate predicate = cb.equal(root.get("paymentName"), bo.getPaymentName());
                predicates.add(predicate);
            }

            if(bo.getState() != null) {
                Predicate statusPredicate = cb.equal(root.get("state"), bo.getState().getCode());
                predicates.add(statusPredicate);
            }

            if(bo.getRefundLocked() != null) {
                Predicate statusPredicate = cb.equal(root.get("refundLocked"), bo.getRefundLocked().getCode());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    private String generateTranId() {
        return IdGenerator.generateInstantTranId();
    }
}
