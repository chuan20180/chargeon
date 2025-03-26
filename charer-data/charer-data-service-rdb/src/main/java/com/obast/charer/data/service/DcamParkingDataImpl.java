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
import com.obast.charer.data.business.IDcamParkingData;
import com.obast.charer.data.dao.DcamParkingRepository;
import com.obast.charer.data.model.device.TbDcamParking;
import com.obast.charer.qo.DcamParkingQueryBo;
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
import com.obast.charer.model.device.DcamParking;

@Primary
@Service
@RequiredArgsConstructor
public class DcamParkingDataImpl extends AbstractCommonData<DcamParkingQueryBo>
        implements IDcamParkingData, IJPACommData<DcamParking, String>, IJPACommonData<DcamParking, DcamParkingQueryBo, String> {

    private final DcamParkingRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbDcamParking.class;
    }

    @Override
    public Class<?> getTClass() {
        return DcamParking.class;
    }

    @Override
    public Paging<DcamParking> findPage(PageRequest<DcamParkingQueryBo> request) {
        Specification<TbDcamParking> specification = buildSpecification(request.getData());
        Page<TbDcamParking> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbDcamParking> list = page.getContent();
        long total = page.getTotalElements();

        List<DcamParking> newList = new ArrayList<>();
        for(TbDcamParking tbDcamParking: list) {
            newList.add(fillData(tbDcamParking));
        }
        return new Paging<>(total, newList);
    }

    @Override
    public List<DcamParking> findList(DcamParkingQueryBo queryBo) {
        Specification<TbDcamParking> specification = buildSpecification(queryBo);
        List<TbDcamParking> list = baseRepository.findAll(specification);
        List<DcamParking> newList = new ArrayList<>();
        for(TbDcamParking tbDcamParking: list) {
            newList.add(fillData(tbDcamParking));
        }
        return newList;
    }

    @Override
    public List<DcamParking> findByDcamId(String dcamId) {
        Specification<TbDcamParking> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("dcamId"), dcamId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbDcamParking> list = baseRepository.findAll(specification);
        List<DcamParking> newList = new ArrayList<>();
        for(TbDcamParking tbDcamParking: list) {
            newList.add(fillData(tbDcamParking));
        }
        return newList;
    }

    @Override
    public List<DcamParking> findByParkingId(String parkingId) {
        Specification<TbDcamParking> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("parkingId"), parkingId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbDcamParking> list = baseRepository.findAll(specification);
        List<DcamParking> newList = new ArrayList<>();
        for(TbDcamParking tbDcamParking: list) {
            newList.add(fillData(tbDcamParking));
        }
        return newList;
    }

    @Override
    public DcamParking findByDcamIdAndName(String dcamId, String name) {
        Specification<TbDcamParking> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("dcamId"), dcamId);
            predicates.add(predicate);

            Predicate namePredicate = cb.equal(root.get("name"), name);
            predicates.add(namePredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbDcamParking entity = baseRepository.findOne(specification).orElse(null);
        return fillData(entity);
    }

    @Transactional
    @Override
    public DcamParking add(DcamParking dcamParking) {
        TbDcamParking bo = new TbDcamParking();
        ReflectUtil.copyNoNulls(dcamParking, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public DcamParking update(DcamParking station) {
        TbDcamParking bo = null;
        if (StringUtils.isNotBlank(station.getId())) {
            bo = baseRepository.findById(station.getId()).orElse(null);
        }
        if (bo == null) {
            throw new BizException(ErrCode.STATION_NOT_FOUND);
        }
        ReflectUtil.copyNoNulls(station, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private DcamParking fillData(TbDcamParking tbDcamParking) {
        return MapstructUtils.convert(tbDcamParking, DcamParking.class);
    }

    public Specification<TbDcamParking> buildSpecification(DcamParkingQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getDcamId())) {
                Predicate predicate = cb.equal(root.get("dcamId"), bo.getDcamId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getParkingId())) {
                Predicate predicate = cb.equal(root.get("parkingId"), bo.getParkingId());
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
