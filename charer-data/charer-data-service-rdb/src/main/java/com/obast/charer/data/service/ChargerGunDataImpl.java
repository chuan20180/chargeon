package com.obast.charer.data.service;

import cn.hutool.core.util.ObjectUtil;
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
import com.obast.charer.data.business.IChargerGunData;
import com.obast.charer.data.dao.ChargerGunRepository;
import com.obast.charer.data.dao.ChargerRepository;
import com.obast.charer.data.model.device.TbCharger;
import com.obast.charer.data.model.device.TbChargerGun;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.qo.ChargerGunQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class ChargerGunDataImpl extends AbstractCommonData<ChargerGunQueryBo>
        implements IChargerGunData, IJPACommData<ChargerGun, String>, IJPACommonData<ChargerGun, ChargerGunQueryBo, String> {


    private final ChargerGunRepository baseRepository;

    private final ChargerRepository chargerRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbChargerGun.class;
    }

    @Override
    public Class<?> getTClass() {
        return ChargerGun.class;
    }

    @Override
    public Paging<ChargerGun> findPage(PageRequest<ChargerGunQueryBo> request) {
        Specification<TbChargerGun> specification = buildSpecification(request.getData());
        Page<TbChargerGun> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbChargerGun> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, ChargerGun.class));
    }

    @Override
    public List<ChargerGun> findList(ChargerGunQueryBo bo) {
        Specification<TbChargerGun> specification = buildSpecification(bo);

        List<TbChargerGun> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, ChargerGun.class);
    }

    @Override
    public List<ChargerGun> findByStationId(String stationId) {
        List<TbCharger> chargers = chargerRepository.findAllByStationId(stationId);
        if(ObjectUtil.isEmpty(chargers)) {
            return new ArrayList<>();
        }
        List<String> chargerIds = chargers.stream().map(TbCharger::getId).collect(Collectors.toList());
        Specification<TbChargerGun> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(root.get("chargerId").in(chargerIds));
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbChargerGun> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, ChargerGun.class);
    }

    @Override
    public List<ChargerGun> findByChargerId(String chargerId) {
        ChargerGunQueryBo bo = new ChargerGunQueryBo();
        bo.setChargerId(chargerId);
        Specification<TbChargerGun> specification = buildSpecification(bo);
        List<TbChargerGun> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, ChargerGun.class);
    }

    @Override
    public boolean checkParkingUnique(ChargerGun chargerGun) {
        Specification<TbChargerGun> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("parkingId"), chargerGun.getParkingId()));
            predicates.add(cb.notEqual(root.get("id"), chargerGun.getId()));
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbChargerGun> list = baseRepository.findAll(specification);
        return list.isEmpty();
    }

    @Override
    public ChargerGun findByChargerIdAndGunNo(String chargerId, String gunNo) {
        ChargerGunQueryBo bo = new ChargerGunQueryBo();
        bo.setChargerId(chargerId);
        bo.setNo(gunNo);
        Specification<TbChargerGun> specification = buildSpecification(bo);
        TbChargerGun entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, ChargerGun.class);
    }

    @Override
    public ChargerGun findByParkingId(String parkingId) {
        Specification<TbChargerGun> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("parkingId"), parkingId));
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbChargerGun entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, ChargerGun.class);
    }


    @Transactional
    @Override
    public ChargerGun add(ChargerGun chargerGun) {
        TbChargerGun bo = new TbChargerGun();
        ReflectUtil.copyNoNulls(chargerGun, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), ChargerGun.class);
    }

    @Transactional
    @Override
    public ChargerGun update(ChargerGun chargerGun) {
        TbChargerGun bo = chargerGun.to(TbChargerGun.class);
        if (org.apache.commons.lang3.StringUtils.isBlank(bo.getId())) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), ChargerGun.class);
    }

    @Override
    public void initData() {
        ChargerGunQueryBo bo = new ChargerGunQueryBo();
        Specification<TbChargerGun> specification = buildSpecification(bo);
        List<TbChargerGun> list = baseRepository.findAll(specification);
        for(TbChargerGun tbChargerGun: list) {
            tbChargerGun.setState(ChargerGunStateEnum.Unknow);
            baseRepository.save(tbChargerGun);
        }
    }

    public Specification<TbChargerGun> buildSpecification(ChargerGunQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getStationId())) {
                List<String> chargerIds = new ArrayList<>(List.of("-1"));
                List<TbCharger> tbChargers = chargerRepository.findAllByStationId(bo.getStationId());
                if(ObjectUtil.isNotEmpty(tbChargers)) {
                    chargerIds.addAll(tbChargers.stream().map(TbCharger::getId).collect(Collectors.toList()));
                }
                Predicate predicate = root.get("chargerId").in(chargerIds);
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getChargerId())) {
                Predicate statusPredicate = cb.equal(root.get("chargerId"), bo.getChargerId());
                predicates.add(statusPredicate);
            }

            if(bo.getNo() != null) {
                Predicate noPredicate = cb.equal(root.get("no"), bo.getNo());
                predicates.add(noPredicate);
            }

            if(bo.getState() != null) {
                javax.persistence.criteria.Predicate statusPredicate = cb.equal(root.get("state"), bo.getState().getCode());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
