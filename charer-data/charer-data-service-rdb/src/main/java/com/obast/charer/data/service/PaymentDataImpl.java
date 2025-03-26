package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.enums.PaymentIdentifierEnum;
import com.obast.charer.qo.PaymentQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.PaymentRepository;
import com.obast.charer.data.model.TbPayment;
import com.obast.charer.data.platform.IPaymentData;
import com.obast.charer.model.payment.Payment;
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
public class PaymentDataImpl extends AbstractCommonData<PaymentQueryBo>
        implements IPaymentData, IJPACommData<Payment, String>, IJPACommonData<Payment, PaymentQueryBo, String> {

    private final PaymentRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() { return baseRepository; }

    @Override
    public Class<?> getJpaRepositoryClass() { return TbPayment.class; }

    @Override
    public Class<?> getTClass() { return Payment.class; }

    @Override
    public Paging<Payment> findPage(PageRequest<PaymentQueryBo> request) {
        Specification<TbPayment> specification = buildSpecification(request.getData());
        Page<TbPayment> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbPayment> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Payment.class));
    }

    @Override
    public List<Payment> findList(PaymentQueryBo queryBo) {
        Specification<TbPayment> specification = buildSpecification(queryBo);
        List<TbPayment> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Payment.class);
    }

    @Override
    public Payment findByIdentifier(PaymentIdentifierEnum identifier) {
        Specification<TbPayment> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("identifier"), identifier);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbPayment tbPayment = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(tbPayment, Payment.class);
    }

    public Specification<TbPayment> buildSpecification(PaymentQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getName())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate predicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("name"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getName() + "%");
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getIdentifier())) {
                Predicate predicate = cb.equal(root.get("identifier"), bo.getIdentifier());
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
