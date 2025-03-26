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
import com.obast.charer.data.business.ITopupData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.dao.TopupRepository;
import com.obast.charer.data.model.TbTopup;
import com.obast.charer.enums.LockEnum;
import com.obast.charer.enums.TopupSourceEnum;
import com.obast.charer.enums.TopupStateEnum;
import com.obast.charer.model.topup.Topup;
import com.obast.charer.qo.TopupQueryBo;
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
import java.util.Map;

@Primary
@Service
@RequiredArgsConstructor
public class TopupDataImpl extends AbstractCommonData<TopupQueryBo>
        implements ITopupData, IJPACommData<Topup, String>, IJPACommonData<Topup, TopupQueryBo, String> {

    private final TopupRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbTopup.class;
    }

    @Override
    public Class<?> getTClass() {
        return Topup.class;
    }

    @Override
    public Map<String, Object> findSummary() {
        return baseRepository.findSummary();
    }

    @Override
    public Map<String, Object> findSummaryByCreateTime(String startTime, String endTime) {
        return baseRepository.findSummaryByCreateTime(startTime, endTime);
    }

    @Override
    public Topup findById(String id) {
        return MapstructUtils.convert(baseRepository.findById(id).orElse(null), Topup.class);
    }

    @Override
    public Paging<Topup> findPage(PageRequest<TopupQueryBo> request) {
        Specification<TbTopup> specification = buildSpecification(request.getData());
        Page<TbTopup> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbTopup> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Topup.class));
    }

    @Override
    public List<Topup> findList(TopupQueryBo queryBo) {
        Specification<TbTopup> specification = buildSpecification(queryBo);
        List<TbTopup> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Topup.class);
    }

    @Override
    public Topup findByTranId(String tranId) {
        Specification<TbTopup> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("tranId"), tranId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbTopup entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, Topup.class);
    }

    @Override
    public List<Topup> findByCustomerIdAndState(String customerId, TopupStateEnum state) {
        Specification<TbTopup> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("customerId"), customerId));
            predicates.add(cb.equal(root.get("state"), state));
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        Sort sort = Sort.by(Sort.Order.desc("paidAmount"), Sort.Order.asc("refundedAmount"));
        List<TbTopup> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Topup.class);
    }

    @Override
    public List<Topup> findRefundableByCustomerId(String customerId) {
        Specification<TbTopup> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("customerId"), customerId));
            predicates.add(cb.equal(root.get("state"), TopupStateEnum.Successful));
            predicates.add(cb.equal(root.get("source"), TopupSourceEnum.Online));
            predicates.add(cb.equal(root.get("refundLocked"), LockEnum.UnLocked));
            predicates.add(cb.greaterThan(root.get("paidAmount"), new BigDecimal(0)));
            predicates.add(cb.gt(cb.diff(root.get("paidAmount"), root.get("refundedAmount")), cb.literal(new BigDecimal(0))));
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        Sort sort = Sort.by(Sort.Order.desc("paidAmount"), Sort.Order.asc("refundedAmount"));
        List<TbTopup> list = baseRepository.findAll(specification, sort);
        return MapstructUtils.convert(list, Topup.class);
    }

    /**
     * 获取充值总金额(到账总金额)根据客户id
     */
    @Override
    public BigDecimal findTopupAmountByCustomerId(String customerId) {
        return baseRepository.findTopupAmountByCustomerId(customerId);
    }

    /*
     * 生成订单
     */
    @Override
    public Topup add(Topup topup) {
        topup.setTranId(this.generateTranId());
        TbTopup tbTopup = MapstructUtils.convert(topup, TbTopup.class);
        if(tbTopup == null) {
            return null;
        }
        return MapstructUtils.convert(baseRepository.saveAndFlush(tbTopup), Topup.class);
    }

    @Transactional
    @Override
    public Topup update(Topup entity) {
        TbTopup bo = baseRepository.findById(entity.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(entity, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Topup.class);
    }

    public Specification<TbTopup> buildSpecification(TopupQueryBo bo) {
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

            if(bo.getSource() != null) {
                Predicate statusPredicate = cb.equal(root.get("source"), bo.getSource().getCode());
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

            if(bo.getStates() != null && !bo.getStates().isEmpty()) {
                Predicate statusPredicate = root.get("state").in(bo.getStates());
                predicates.add(statusPredicate);
            }

            if(bo.getRefundLocked() != null) {
                Predicate statusPredicate = cb.equal(root.get("refundLocked"), bo.getRefundLocked().getCode());
                predicates.add(statusPredicate);
            }

            if(bo.getCreateTime() != null && bo.getCreateTime().length > 0) {
                Date startTime = null;
                Date endTime  = null;
                if(bo.getCreateTime().length == 1) {
                    startTime = bo.getCreateTime()[0];
                } else if(bo.getCreateTime().length == 2) {
                    startTime = bo.getCreateTime()[0];
                    endTime  = bo.getCreateTime()[1];
                }

                if(startTime != null) {
                    Predicate predicate = cb.greaterThanOrEqualTo(root.get("createTime"), startTime);
                    predicates.add(predicate);
                }

                if(endTime != null) {
                    Predicate predicate = cb.lessThanOrEqualTo(root.get("createTime"), endTime);
                    predicates.add(predicate);
                }
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    private String generateTranId() {
        return IdGenerator.generateTopupTranId();
    }
}
