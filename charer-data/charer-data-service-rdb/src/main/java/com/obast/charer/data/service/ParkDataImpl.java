package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IParkData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.dao.ParkRepository;
import com.obast.charer.data.model.TbPark;
import com.obast.charer.enums.ParkStateEnum;
import com.obast.charer.model.park.Park;
import com.obast.charer.qo.ParkQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Primary
@Service
@RequiredArgsConstructor
public class ParkDataImpl extends AbstractCommonData<ParkQueryBo>
        implements IParkData, IJPACommData<Park, String>, IJPACommonData<Park, ParkQueryBo, String> {

    private final ParkRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbPark.class;
    }

    @Override
    public Class<?> getTClass() {
        return Park.class;
    }

    @Override
    public Paging<Park> findPage(PageRequest<ParkQueryBo> request) {
        Specification<TbPark> specification = buildSpecification(request.getData());
        Page<TbPark> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbPark> list = page.getContent();
        long total = page.getTotalElements();

        List<Park> newList = new ArrayList<>();
        for(TbPark tbPark: list) {
            newList.add(fillData(tbPark));
        }
        return new Paging<>(total, newList);
    }

    @Override
    public List<Park> findList(ParkQueryBo queryBo) {
        Specification<TbPark> specification = buildSpecification(queryBo);
        List<TbPark> list = baseRepository.findAll(specification);
        List<Park> newList = new ArrayList<>();
        for(TbPark tbPark: list) {
            newList.add(fillData(tbPark));
        }
        return newList;
    }

    @Override
    public Park findOnParkingByParkingId(String parkingId) {
        Specification<TbPark> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate stationIdPredicate = cb.equal(root.get("parkingId"), parkingId);
            predicates.add(stationIdPredicate);

            Predicate statePredicate = cb.equal(root.get("state"), ParkStateEnum.Entered);
            predicates.add(statePredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbPark entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, Park.class);
    }

    @Transactional
    @Override
    public Park add(Park park) {

        park.setTranId(this.generateTranId());

        TbPark bo = new TbPark();
        ReflectUtil.copyNoNulls(park, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public Park update(Park park) {
        TbPark bo = null;
        if (StringUtils.isNotBlank(park.getId())) {
            bo = baseRepository.findById(park.getId()).orElse(null);
        }
        if (bo == null) {
            throw new BizException(ErrCode.STATION_NOT_FOUND);
        }
        ReflectUtil.copyNoNulls(park, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private Park fillData(TbPark tbPark) {
        return MapstructUtils.convert(tbPark, Park.class);
    }

    public Specification<TbPark> buildSpecification(ParkQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getKeyword())) {
                String keyword = bo.getKeyword();
                Predicate predicateOr1 = cb.like(root.get("tranId").as(String.class), "%" + keyword + "%");

                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate predicateOr2 = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("stationName"), cb.literal(String.format("$.%s", requestLang))), "%" + keyword + "%");

                Predicate predicateOr3 = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("parkingName"), cb.literal(String.format("$.%s", requestLang))), "%" + keyword + "%");

                Predicate predicateOr = cb.or(predicateOr1, predicateOr2, predicateOr3);
                predicates.add(predicateOr);
            }

            if(StringUtils.isNoneBlank(bo.getStationId())) {
                Predicate predicate = cb.equal(root.get("stationId"), bo.getStationId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getParkingId())) {
                Predicate predicate = cb.equal(root.get("parkingId"), bo.getParkingId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getCarPlate())) {
                Predicate predicate = cb.equal(root.get("carPlate"), bo.getCarPlate());
                predicates.add(predicate);
            }

            if(bo.getState() != null) {
                Predicate predicate = cb.equal(root.get("state"), bo.getState().getCode());
                predicates.add(predicate);
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
        return IdGenerator.generateParkTranId();
    }
}
