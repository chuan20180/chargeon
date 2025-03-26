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
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.data.business.IChargerGunData;
import com.obast.charer.data.business.IParkingData;
import com.obast.charer.data.business.IStationData;
import com.obast.charer.data.dao.ParkingRepository;
import com.obast.charer.data.model.TbParking;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.parking.Parking;
import com.obast.charer.model.station.Station;
import com.obast.charer.qo.ParkingQueryBo;
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
public class ParkingDataImpl extends AbstractCommonData<ParkingQueryBo>
        implements IParkingData, IJPACommData<Parking, String>, IJPACommonData<Parking, ParkingQueryBo, String> {

    private final ParkingRepository baseRepository;

    private final IStationData stationData;

    private final IChargerData chargerData;

    private final IChargerGunData chargerGunData;




    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbParking.class;
    }

    @Override
    public Class<?> getTClass() {
        return Parking.class;
    }

    @Override
    public Paging<Parking> findPage(PageRequest<ParkingQueryBo> request) {
        Specification<TbParking> specification = buildSpecification(request.getData());
        Page<TbParking> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbParking> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Parking.class));
    }

    @Override
    public List<Parking> findList(ParkingQueryBo queryBo) {
        Specification<TbParking> specification = buildSpecification(queryBo);
        List<TbParking> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Parking.class);
    }

    @Override
    public Parking findByStationIdAndNo(String stationId, String no) {
        Specification<TbParking> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate stationIdPredicate = cb.equal(root.get("stationId"), stationId);
            predicates.add(stationIdPredicate);

            Predicate noPredicate = cb.equal(root.get("no"), no);
            predicates.add(noPredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbParking entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, Parking.class);
    }


    @Transactional
    @Override
    public Parking add(Parking parking) {
        TbParking bo = new TbParking();
        ReflectUtil.copyNoNulls(parking, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Parking.class);
    }

    @Transactional
    @Override
    public Parking update(Parking parking) {
        TbParking bo = null;
        if (StringUtils.isNotBlank(parking.getId())) {
            bo = baseRepository.findById(parking.getId()).orElse(null);
        }
        if (bo == null) {
            throw new BizException(ErrCode.PARKING_NOT_FOUND);
        }
        ReflectUtil.copyNoNulls(parking, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Parking.class);
    }

    public Specification<TbParking> buildSpecification(ParkingQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getStationId())) {
                Predicate predicate = cb.equal(root.get("stationId"), bo.getStationId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getName())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("name"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getName() + "%");
                predicates.add(namePredicate);
            }

            if(StringUtils.isNoneBlank(bo.getNo())) {
                Predicate predicate = cb.equal(root.get("no"), bo.getNo());
                predicates.add(predicate);
            }



            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
