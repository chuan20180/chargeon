package com.obast.charer.data.service;

import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.StationRepository;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.model.device.TbCharger;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.qo.StationQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IStationData;
import com.obast.charer.data.model.TbStation;
import com.obast.charer.model.station.Station;
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
import java.util.List;
import java.util.Locale;


@Primary
@Service
@RequiredArgsConstructor
public class StationDataImpl extends AbstractCommonData<StationQueryBo>
        implements IStationData, IJPACommData<Station, String>, IJPACommonData<Station, StationQueryBo, String> {

    private final StationRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbStation.class;
    }

    @Override
    public Class<?> getTClass() {
        return Station.class;
    }

    @Override
    public Paging<Station> findPage(PageRequest<StationQueryBo> request) {
        Specification<TbStation> specification = buildSpecification(request.getData());
        Page<TbStation> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbStation> list = page.getContent();
        long total = page.getTotalElements();

        List<Station> newList = new ArrayList<>();
        for(TbStation tbStation: list) {
            newList.add(fillData(tbStation));
        }
        return new Paging<>(total, newList);
    }

    @Override
    public List<Station> findList(StationQueryBo queryBo) {
        Specification<TbStation> specification = buildSpecification(queryBo);
        List<TbStation> list = baseRepository.findAll(specification);
        List<Station> newList = new ArrayList<>();
        for(TbStation tbStation: list) {
            newList.add(fillData(tbStation));
        }
        return newList;
    }

    @Override
    public List<Station> findNoAgentList(StationQueryBo bo) {
        Specification<TbStation> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.isNull(root.get("agentId"));
            predicates.add(predicate);

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbStation> list = baseRepository.findAll(specification);
        List<Station> newList = new ArrayList<>();
        for(TbStation tbStation: list) {
            newList.add(fillData(tbStation));
        }
        return newList;
    }

    @Override
    public List<Station> findByAgentId(String agentId) {
        Specification<TbStation> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("agentId"), agentId);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbStation> list = baseRepository.findAll(specification);
        List<Station> newList = new ArrayList<>();
        for(TbStation tbStation: list) {
            newList.add(fillData(tbStation));
        }
        return newList;
    }

    @Transactional
    @Override
    public Station add(Station station) {
        TbStation bo = station.to(TbStation.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public Station update(Station station) {
        TbStation bo = baseRepository.findById(station.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(station, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private Station fillData(TbStation tbStation) {
        return MapstructUtils.convert(tbStation, Station.class);
    }

    public Specification<TbStation> buildSpecification(StationQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getKeyword())) {
                String keyword = bo.getKeyword();

                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate predicate = cb.or(
                    cb.like(cb.function("JSON_EXTRACT", String.class, root.get("name"), cb.literal(String.format("$.%s", requestLang))), "%" + keyword + "%"),
                    cb.like(cb.function("JSON_EXTRACT", String.class, root.get("address"), cb.literal(String.format("$.%s", requestLang))), "%" + keyword + "%")
                );
                predicates.add(predicate);
            }

            //name
            if(StringUtils.isNoneBlank(bo.getName())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("name"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getName() + "%");
                predicates.add(namePredicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
