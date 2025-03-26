package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IRefundBalanceData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.dao.RefundBalanceRepository;
import com.obast.charer.data.model.TbRefundBalance;
import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.model.refund.RefundBalance;
import com.obast.charer.qo.RefundBalanceQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class RefundBalanceDataImpl extends AbstractCommonData<RefundBalanceQueryBo>
        implements IRefundBalanceData, IJPACommData<RefundBalance, String>, IJPACommonData<RefundBalance, RefundBalanceQueryBo, String> {

    private final RefundBalanceRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbRefundBalance.class;
    }

    @Override
    public Class<?> getTClass() {
        return RefundBalance.class;
    }

    @Override
    public Paging<RefundBalance> findPage(PageRequest<RefundBalanceQueryBo> request) {
        Specification<TbRefundBalance> specification = buildSpecification(request.getData());
        Page<TbRefundBalance> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbRefundBalance> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, RefundBalance.class));
    }

    @Override
    public List<RefundBalance> findList(RefundBalanceQueryBo queryBo) {
        Specification<TbRefundBalance> specification = buildSpecification(queryBo);
        List<TbRefundBalance> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, RefundBalance.class);
    }

    @Override
    public List<RefundBalance> findListByRefundId(String refundId) {
        Specification<TbRefundBalance> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate1 = cb.equal(root.get("refundId"), refundId);
            predicates.add(predicate1);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbRefundBalance> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, RefundBalance.class);
    }

    @Override
    public RefundBalance findByTranId(String tranId) {
        Specification<TbRefundBalance> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate1 = cb.equal(root.get("tranId"), tranId);
            predicates.add(predicate1);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbRefundBalance entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, RefundBalance.class);
    }

    @Override
    public BigDecimal findRefundedAmountByCustomer(String customerId) {
       return baseRepository.findRefundedAmountByCustomerId(customerId);
    }

    @Transactional
    @Override
    public RefundBalance add(RefundBalance refundBalance) {
        TbRefundBalance bo = refundBalance.to(TbRefundBalance.class);
        bo.setTranId(this.generateTranId());
        bo.setState(RefundStateEnum.Pending);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), RefundBalance.class);
    }

    public Specification<TbRefundBalance> buildSpecification(RefundBalanceQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getRefundId())) {
                predicates.add(cb.equal(root.get("refundId"), bo.getRefundId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    private String generateTranId() {
        return IdGenerator.generateRefundBalanceTranId();
    }

}
