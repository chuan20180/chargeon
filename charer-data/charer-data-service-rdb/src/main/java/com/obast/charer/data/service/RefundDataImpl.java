package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.dao.RefundRepository;
import com.obast.charer.data.business.IRefundData;
import com.obast.charer.data.model.TbRefund;
import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.model.refund.Refund;
import com.obast.charer.qo.RefundQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Primary
@Service
@RequiredArgsConstructor
public class RefundDataImpl extends AbstractCommonData<RefundQueryBo>
        implements IRefundData, IJPACommData<Refund, String>, IJPACommonData<Refund, RefundQueryBo, String> {

    private final RefundRepository baseRepository;


    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbRefund.class;
    }

    @Override
    public Class<?> getTClass() {
        return Refund.class;
    }

    @Override
    public Paging<Refund> findPage(PageRequest<RefundQueryBo> request) {
        Specification<TbRefund> specification = buildSpecification(request.getData());
        Page<TbRefund> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbRefund> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Refund.class));
    }

    @Override
    public List<Refund> findList(RefundQueryBo queryBo) {
        Specification<TbRefund> specification = buildSpecification(queryBo);
        List<TbRefund> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Refund.class);
    }

    @Override
    public Refund add(Refund refund) {
        refund.setTranId(this.generateTranId());
        refund.setState(RefundStateEnum.Pending);
        TbRefund tbRefund = MapstructUtils.convert(refund, TbRefund.class);
        if(tbRefund == null) {
            return null;
        }

        TbRefund savedRefund = baseRepository.saveAndFlush(tbRefund);

        return MapstructUtils.convert(savedRefund, Refund.class);
    }

    @Override
    public Refund findByTranId(String id) {
        Specification<TbRefund> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate1 = cb.equal(root.get("tranId"), id);
            predicates.add(predicate1);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbRefund tbRefund = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(tbRefund, Refund.class);

    }

    public Specification<TbRefund> buildSpecification(RefundQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getKeyword())) {
                String keyword = bo.getKeyword();

                Predicate predicate = cb.or(
                        cb.like( root.get("userName"), "%" + keyword + "%"),
                        cb.like( root.get("tranId"), "%" + keyword + "%")
                );
                predicates.add(predicate);
            }


            if(bo.getState() != null) {
                Predicate statusPredicate = cb.equal(root.get("state"), bo.getState().getCode());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    private String generateTranId() {
        return IdGenerator.generateRefundTranId();
    }
}
