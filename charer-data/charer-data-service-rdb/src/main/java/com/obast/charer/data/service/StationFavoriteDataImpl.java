package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.StationFavoriteRepository;
import com.obast.charer.qo.StationFavoriteQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IStationFavoriteData;
import com.obast.charer.data.model.TbStationFavorite;
import com.obast.charer.model.station.StationFavorite;
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
public class StationFavoriteDataImpl extends AbstractCommonData<StationFavoriteQueryBo>
        implements IStationFavoriteData, IJPACommData<StationFavorite, String>, IJPACommonData<StationFavorite, StationFavoriteQueryBo, String> {

    private final StationFavoriteRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbStationFavorite.class;
    }

    @Override
    public Class<?> getTClass() {
        return StationFavorite.class;
    }

    @Override
    public Paging<StationFavorite> findPage(PageRequest<StationFavoriteQueryBo> request) {
        Specification<TbStationFavorite> specification = buildSpecification(request.getData());
        Page<TbStationFavorite> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbStationFavorite> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, StationFavorite.class));
    }

    @Override
    public List<StationFavorite> findList(StationFavoriteQueryBo queryBo) {
        Specification<TbStationFavorite> specification = buildSpecification(queryBo);
        List<TbStationFavorite> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, StationFavorite.class);
    }

    public Specification<TbStationFavorite> buildSpecification(StationFavoriteQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getStationId())) {
                Predicate statusPredicate = cb.equal(root.get("stationId"), bo.getStationId());
                predicates.add(statusPredicate);
            }

            if(StringUtils.isNoneBlank(bo.getCustomerId())) {
                Predicate statusPredicate = cb.equal(root.get("customerId"), bo.getCustomerId());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
