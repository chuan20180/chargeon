package com.obast.charer.data.service;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.PromotionRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IPromotionData;
import com.obast.charer.data.model.TbPromotion;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PromotionScopeEnum;
import com.obast.charer.enums.PromotionTypeEnum;
import com.obast.charer.model.promotion.Promotion;
import com.obast.charer.qo.PromotionQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Primary
@Service
@RequiredArgsConstructor
public class PromotionDataImpl extends AbstractCommonData<PromotionQueryBo>
        implements IPromotionData, IJPACommData<Promotion, String>, IJPACommonData<Promotion, PromotionQueryBo, String> {

    private final PromotionRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() { return baseRepository; }

    @Override
    public Class<?> getJpaRepositoryClass() { return TbPromotion.class;  }

    @Override
    public Class<?> getTClass() {
        return Promotion.class;
    }


    @Override
    public Paging<Promotion> findPage(PageRequest<PromotionQueryBo> request) {
        Specification<TbPromotion> specification = buildSpecification(request.getData());
        Page<TbPromotion> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbPromotion> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Promotion.class));
    }

    @Override
    public List<Promotion> findList(PromotionQueryBo queryBo) {
        Specification<TbPromotion> specification = buildSpecification(queryBo);
        List<TbPromotion> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Promotion.class);
    }

    @Override
    public List<Promotion> findAvailableByStationId(String stationId) {
        Specification<TbPromotion> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate scopePredicate = cb.or(
                    cb.equal(root.get("scope"), PromotionScopeEnum.All.getCode()),
                    cb.and(
                            cb.equal(root.get("scope"), PromotionScopeEnum.Part.getCode()),
                            cb.like(root.get("stationIds"), "%"+stationId+"%")
                    )
            );
            predicates.add(scopePredicate);

            predicates.add(cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode()));
            predicates.add(cb.lessThanOrEqualTo(root.get("startTime"), new Date()));
            predicates.add(cb.greaterThanOrEqualTo(root.get("endTime"), new Date()));


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbPromotion> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Promotion.class);
    }


    @Override
    public Promotion findByName(String name) {
        PromotionQueryBo bo = new PromotionQueryBo();
        bo.setName(name);
        Specification<TbPromotion> specification = buildSpecification(bo);
        TbPromotion entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, Promotion.class);
    }

    @Override
    public List<Promotion> findByStationId(String stationId) {
        Specification<TbPromotion> specification = (root, query, cb) -> {
            Predicate predicate = cb.or(
                    cb.equal(root.get("scope"), PromotionScopeEnum.All.getCode()),
                    cb.and(
                            cb.equal(root.get("scope"), PromotionScopeEnum.Part.getCode()),
                            cb.like(root.get("station_ids"), "%"+stationId+"%")
                    )
            );
            return query.where(predicate).getRestriction();
        };
        List<TbPromotion> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Promotion.class);
    }

    //查找所有有效的服务费打折活动（根据场站id）

    @Override
    public Promotion findMaxDiscountByStationId(String stationId) {
        Specification<TbPromotion> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate scopePredicate = cb.or(
                    cb.equal(root.get("scope"), PromotionScopeEnum.All.getCode()),
                    cb.and(
                            cb.equal(root.get("scope"), PromotionScopeEnum.Part.getCode()),
                            cb.like(root.get("stationIds"), "%"+stationId+"%")
                    )
            );
            predicates.add(scopePredicate);

            Predicate statusPredicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
            predicates.add(statusPredicate);

            Predicate startPredicate = cb.lessThanOrEqualTo(root.get("startTime"), new Date());
            predicates.add(startPredicate);

            Predicate endPredicate = cb.greaterThanOrEqualTo(root.get("endTime"), new Date());
            predicates.add(endPredicate);

            Expression<String> orderField = cb.function("JSON_EXTRACT", String.class,  root.get("properties"), cb.literal("$.discount"));

            return query.where(predicates.toArray(Predicate[]::new)).orderBy(cb.desc(orderField)).getRestriction();
        };

        List<TbPromotion> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Promotion.class).stream().findFirst().orElse(null);
    }

    @Override
    public Promotion findMaxServiceDiscountByStationId(String stationId) {
        Specification<TbPromotion> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate scopePredicate = cb.or(
                    cb.equal(root.get("scope"), PromotionScopeEnum.All.getCode()),
                    cb.and(
                            cb.equal(root.get("scope"), PromotionScopeEnum.Part.getCode()),
                            cb.like(root.get("stationIds"), "%"+stationId+"%")
                    )
            );
            predicates.add(scopePredicate);

            Predicate typePredicate = root.get("type").in(PromotionTypeEnum.ServiceDiscount.getCode(),PromotionTypeEnum.ServiceAndParkDiscount.getCode());

            predicates.add(typePredicate);

            Predicate statusPredicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
            predicates.add(statusPredicate);

            Predicate startPredicate = cb.lessThanOrEqualTo(root.get("startTime"), new Date());
            predicates.add(startPredicate);

            Predicate endPredicate = cb.greaterThanOrEqualTo(root.get("endTime"), new Date());
            predicates.add(endPredicate);

            Expression<String> orderField = cb.function("JSON_EXTRACT", String.class,  root.get("properties"), cb.literal("$.discount"));

            return query.where(predicates.toArray(Predicate[]::new)).orderBy(cb.desc(orderField)).getRestriction();
        };

        List<TbPromotion> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Promotion.class).stream().findFirst().orElse(null);
    }

    @Override
    public Promotion findMaxParkDiscountByStationId(String stationId) {

        Specification<TbPromotion> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate scopePredicate = cb.or(
                    cb.equal(root.get("scope"), PromotionScopeEnum.All.getCode()),
                    cb.and(
                            cb.equal(root.get("scope"), PromotionScopeEnum.Part.getCode()),
                            cb.like(root.get("stationIds"), "%"+stationId+"%")
                    )
            );
            predicates.add(scopePredicate);

            Predicate typePredicate = root.get("type").in(PromotionTypeEnum.ServiceAndParkDiscount.getCode());

            predicates.add(typePredicate);

            Predicate statusPredicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
            predicates.add(statusPredicate);

            Predicate startPredicate = cb.lessThanOrEqualTo(root.get("startTime"), new Date());
            predicates.add(startPredicate);

            Predicate endPredicate = cb.greaterThanOrEqualTo(root.get("endTime"), new Date());
            predicates.add(endPredicate);

            Expression<String> orderField = cb.function("JSON_EXTRACT", String.class,  root.get("properties"), cb.literal("$.discount"));

            return query.where(predicates.toArray(Predicate[]::new)).orderBy(cb.desc(orderField)).getRestriction();
        };

        List<TbPromotion> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Promotion.class).stream().findFirst().orElse(null);
    }

    @Override
    public Promotion add(Promotion promotion) {
        promotion.setStatus(EnableStatusEnum.Enabled);
        TbPromotion tbPromotion = baseRepository.saveAndFlush(MapstructUtils.convert(promotion, TbPromotion.class));
        return MapstructUtils.convert(tbPromotion, Promotion.class);
    }

    @Transactional
    @Override
    public Promotion update(Promotion entity) {
        TbPromotion bo = baseRepository.findById(entity.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(entity, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Promotion.class);
    }


    public Specification<TbPromotion> buildSpecification(PromotionQueryBo bo) {
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

            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("apply"), bo.getType().getCode());
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
