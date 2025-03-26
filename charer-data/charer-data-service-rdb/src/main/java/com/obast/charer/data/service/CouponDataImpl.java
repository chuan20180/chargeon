package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.CouponRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.ICouponData;
import com.obast.charer.data.model.TbCoupon;
import com.obast.charer.model.coupon.Coupon;
import com.obast.charer.qo.CouponQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Primary
@Service
@RequiredArgsConstructor
public class CouponDataImpl extends AbstractCommonData<CouponQueryBo>
        implements ICouponData, IJPACommData<Coupon, String>, IJPACommonData<Coupon, CouponQueryBo, String> {

    private final CouponRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() { return baseRepository; }

    @Override
    public Class<?> getJpaRepositoryClass() { return TbCoupon.class;  }

    @Override
    public Class<?> getTClass() {
        return Coupon.class;
    }


    @Override
    public Paging<Coupon> findPage(PageRequest<CouponQueryBo> request) {
        Specification<TbCoupon> specification = buildSpecification(request.getData());
        Page<TbCoupon> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbCoupon> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Coupon.class));
    }

    @Override
    public List<Coupon> findList(CouponQueryBo queryBo) {
        Specification<TbCoupon> specification = buildSpecification(queryBo);
        List<TbCoupon> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Coupon.class);
    }

    @Override
    public Coupon findByName(String name) {
        CouponQueryBo bo = new CouponQueryBo();
        bo.setName(name);
        Specification<TbCoupon> specification = buildSpecification(bo);
        TbCoupon entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, Coupon.class);
    }


    @Override
    public Coupon add(Coupon coupon) {
        TbCoupon tbCoupon = baseRepository.saveAndFlush(coupon.to(TbCoupon.class));
        return MapstructUtils.convert(tbCoupon, Coupon.class);
    }

    public Specification<TbCoupon> buildSpecification(CouponQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getId())) {
                Predicate predicate = cb.equal(root.get("id"), bo.getId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getName())) {
                Predicate predicate = cb.equal(root.get("name"), bo.getName());
                predicates.add(predicate);
            }

            if(bo.getScope() != null) {
                Predicate predicate = cb.equal(root.get("scope"), bo.getScope().getCode());
                predicates.add(predicate);
            }

            if(bo.getApply() != null) {
                Predicate predicate = cb.equal(root.get("apply"), bo.getApply().getCode());
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
