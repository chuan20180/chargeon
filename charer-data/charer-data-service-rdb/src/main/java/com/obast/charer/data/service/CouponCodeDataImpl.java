package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.ICouponCodeData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.dao.CouponCodeRepository;
import com.obast.charer.data.model.TbCouponCode;
import com.obast.charer.enums.CouponApplyEnum;
import com.obast.charer.enums.CouponCodeStateEnum;
import com.obast.charer.enums.PromotionScopeEnum;
import com.obast.charer.model.coupon.CouponCode;
import com.obast.charer.qo.CouponCodeQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class CouponCodeDataImpl extends AbstractCommonData<CouponCodeQueryBo>
        implements ICouponCodeData, IJPACommData<CouponCode, String>, IJPACommonData<CouponCode, CouponCodeQueryBo, String> {

    private final CouponCodeRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbCouponCode.class;
    }

    @Override
    public Class<?> getTClass() {
        return CouponCode.class;
    }

    @Override
    public Paging<CouponCode> findPage(PageRequest<CouponCodeQueryBo> request) {
        Specification<TbCouponCode> specification = buildSpecification(request.getData());
        Page<TbCouponCode> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbCouponCode> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, CouponCode.class));
    }

    @Override
    public List<CouponCode> findList(CouponCodeQueryBo queryBo) {
        Specification<TbCouponCode> specification = buildSpecification(queryBo);
        List<TbCouponCode> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, CouponCode.class);
    }

    @Override
    public List<CouponCode> findByCustomerId(String customerId) {

        Specification<TbCouponCode> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate customerIdPredicate = cb.equal(root.get("customerId"), customerId);
            predicates.add(customerIdPredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        List<TbCouponCode> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, CouponCode.class);
    }

    @Override
    public List<CouponCode> findAvailableByCustomerId(String customerId) {
        Specification<TbCouponCode> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate customerIdPredicate = cb.equal(root.get("customerId"), customerId);
            predicates.add(customerIdPredicate);

            Predicate statePredicate = cb.equal(root.get("state"), CouponCodeStateEnum.UnUsed.getCode());
            predicates.add(statePredicate);

            Predicate expirePredicate = cb.greaterThanOrEqualTo(root.get("expireTime"), new Date());
            predicates.add(expirePredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        List<TbCouponCode> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, CouponCode.class);
    }

    @Override
    public CouponCode findMaxDiscountByCustomerId(String customerId) {
        Specification<TbCouponCode> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate customerIdPredicate = cb.equal(root.get("customerId"), customerId);
            predicates.add(customerIdPredicate);

            Predicate statePredicate = cb.equal(root.get("state"), CouponCodeStateEnum.UnUsed.getCode());
            predicates.add(statePredicate);

            Predicate expirePredicate = cb.greaterThanOrEqualTo(root.get("expireTime"), new Date());
            predicates.add(expirePredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        Sort sort = Sort.by(Sort.Order.desc("amount"));
        List<TbCouponCode> list = baseRepository.findAll(specification, sort);

        if(list.isEmpty()) {
            return null;
        }
        return MapstructUtils.convert(list.get(0), CouponCode.class);
    }

    @Override
    public CouponCode findMaxDiscountByCustomerIdAndStationId(String customerId, String stationId) {
        Specification<TbCouponCode> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate customerIdPredicate = cb.equal(root.get("customerId"), customerId);
            predicates.add(customerIdPredicate);

            Predicate scopePredicate = cb.or(
                    cb.equal(root.get("scope"), PromotionScopeEnum.All.getCode()),
                    cb.and(
                            cb.equal(root.get("scope"), PromotionScopeEnum.Part.getCode()),
                            cb.like(root.get("stationIds"), "%"+stationId+"%")
                    )
            );
            predicates.add(scopePredicate);

            Predicate statePredicate = cb.equal(root.get("state"), CouponCodeStateEnum.UnUsed.getCode());
            predicates.add(statePredicate);


            Predicate expirePredicate = cb.greaterThanOrEqualTo(root.get("expireTime"), new Date());
            predicates.add(expirePredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        Sort sort = Sort.by(Sort.Order.desc("amount"));
        List<TbCouponCode> list = baseRepository.findAll(specification, sort);

        if(list.isEmpty()) {
            return null;
        }
        return MapstructUtils.convert(list.get(0), CouponCode.class);
    }

    @Override
    public CouponCode findMaxServiceDiscountByCustomerIdAndStationId(String customerId, String stationId) {
        Specification<TbCouponCode> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate customerIdPredicate = cb.equal(root.get("customerId"), customerId);
            predicates.add(customerIdPredicate);

            Predicate scopePredicate = cb.or(
                    cb.equal(root.get("scope"), PromotionScopeEnum.All.getCode()),
                    cb.and(
                            cb.equal(root.get("scope"), PromotionScopeEnum.Part.getCode()),
                            cb.like(root.get("stationIds"), "%"+stationId+"%")
                    )
            );
            predicates.add(scopePredicate);

            Predicate remainedAmountPredicate = cb.greaterThan(root.get("remainedAmount"), 0);
            predicates.add(remainedAmountPredicate);

            Predicate applyPredicate = root.get("apply").in(CouponApplyEnum.Service.getCode(),CouponApplyEnum.ServiceAndPark.getCode());
            predicates.add(applyPredicate);

            Predicate expirePredicate = cb.greaterThanOrEqualTo(root.get("expireTime"), new Date());
            predicates.add(expirePredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        Sort sort = Sort.by(Sort.Order.asc("expireTime"));
        List<TbCouponCode> list = baseRepository.findAll(specification, sort);

        if(list.isEmpty()) {
            return null;
        }
        return MapstructUtils.convert(list.get(0), CouponCode.class);
    }

    @Override
    public CouponCode findMaxParkDiscountByCustomerIdAndStationId(String customerId, String stationId) {
        Specification<TbCouponCode> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate scopePredicate = cb.or(
                    cb.equal(root.get("scope"), PromotionScopeEnum.All.getCode()),
                    cb.and(
                            cb.equal(root.get("scope"), PromotionScopeEnum.Part.getCode()),
                            cb.like(root.get("stationIds"), "%"+stationId+"%")
                    )
            );
            predicates.add(scopePredicate);

            Predicate customerIdPredicate = cb.equal(root.get("customerId"), customerId);
            predicates.add(customerIdPredicate);

            Predicate statePredicate = cb.equal(root.get("state"), CouponCodeStateEnum.UnUsed.getCode());
            predicates.add(statePredicate);

            Predicate applyPredicate = root.get("apply").in(CouponApplyEnum.ServiceAndPark.getCode());
            predicates.add(applyPredicate);

            Predicate expirePredicate = cb.greaterThanOrEqualTo(root.get("expireTime"), new Date());
            predicates.add(expirePredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        Sort sort = Sort.by(Sort.Order.desc("amount"));
        List<TbCouponCode> list = baseRepository.findAll(specification, sort);

        if(list.isEmpty()) {
            return null;
        }
        return MapstructUtils.convert(list.get(0), CouponCode.class);
    }

    @Override
    public CouponCode findByTranId(String tranId) {
        CouponCodeQueryBo bo = new CouponCodeQueryBo();
        bo.setTranId(tranId);
        Specification<TbCouponCode> specification = buildSpecification(bo);
        TbCouponCode entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, CouponCode.class);
    }

    @Override
    public List<CouponCode> findByCouponId(String couponId) {

        Specification<TbCouponCode> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate predicate1 = cb.equal(root.get("couponId"), couponId);
            predicates.add(predicate1);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        List<TbCouponCode> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, CouponCode.class);
    }

    @Override
    public CouponCode add(CouponCode couponCode) {
        couponCode.setTranId(this.generateTranId());

        TbCouponCode tbCouponCode = MapstructUtils.convert(couponCode, TbCouponCode.class);
        if(tbCouponCode == null) {
            return null;
        }
        return MapstructUtils.convert(baseRepository.save(tbCouponCode), CouponCode.class);
    }

    public Specification<TbCouponCode> buildSpecification(CouponCodeQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getId())) {
                Predicate predicate = cb.equal(root.get("id"), bo.getId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getCouponId())) {
                Predicate predicate = cb.equal(root.get("couponId"), bo.getCouponId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getTranId())) {
                Predicate predicate = cb.equal(root.get("tranId"), bo.getTranId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getCustomerId())) {
                Predicate predicate = cb.equal(root.get("customerId"), bo.getCustomerId());
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
        return IdGenerator.generateCouponCodeTranId();
    }

}
