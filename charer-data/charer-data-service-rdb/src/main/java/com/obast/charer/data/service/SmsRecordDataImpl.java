package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SmsRecordRepository;
import com.obast.charer.data.business.ISmsRecordData;
import com.obast.charer.data.model.TbSmsRecord;
import com.obast.charer.model.sms.SmsRecord;
import com.obast.charer.qo.SmsRecordQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class SmsRecordDataImpl extends AbstractCommonData<SmsRecordQueryBo>
        implements ISmsRecordData, IJPACommData<SmsRecord, String>, IJPACommonData<SmsRecord, SmsRecordQueryBo, String> {

    private final SmsRecordRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSmsRecord.class;
    }

    @Override
    public Class<?> getTClass() {
        return SmsRecord.class;
    }

    @Override
    public Paging<SmsRecord> findPage(PageRequest<SmsRecordQueryBo> request) {
        Specification<TbSmsRecord> specification = buildSpecification(request.getData());
        Page<TbSmsRecord> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSmsRecord> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SmsRecord.class));
    }

    @Override
    public List<SmsRecord> findList(SmsRecordQueryBo queryBo) {
        Specification<TbSmsRecord> specification = buildSpecification(queryBo);
        List<TbSmsRecord> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SmsRecord.class);
    }

    @Override
    public Long findCountByMobileInMinute(String mobile, Integer minute) {
        Specification<TbSmsRecord> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate mobilePredicate = cb.equal(root.get("mobile"), mobile);
            predicates.add(mobilePredicate);

            Predicate resultPredicate = cb.equal(root.get("result"), 1);
            predicates.add(resultPredicate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, -minute);
            Date date = calendar.getTime();

            Predicate createTimePredicate = cb.greaterThanOrEqualTo(root.get("createTime"), date);
            predicates.add(createTimePredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return baseRepository.count(specification);
    }

    @Override
    public SmsRecord findLastByMobile(String mobile) {
        Specification<TbSmsRecord> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate mobilePredicate = cb.equal(root.get("mobile"), mobile);
            predicates.add(mobilePredicate);

            Predicate resultPredicate = cb.equal(root.get("result"), 1);
            predicates.add(resultPredicate);


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        Sort sort = Sort.by(Sort.Order.desc("createTime"));
        List<TbSmsRecord> list = baseRepository.findAll(specification, sort);
        if(list.isEmpty()) {
            return null;
        }
        return MapstructUtils.convert(list.get(0), SmsRecord.class);
    }

    public Specification<TbSmsRecord> buildSpecification(SmsRecordQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getId())) {
                Predicate predicate = cb.equal(root.get("id"), bo.getId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getMobile())) {
                Predicate predicate = cb.equal(root.get("mobile"), bo.getMobile());
                predicates.add(predicate);
            }

            if(bo.getResult() != null) {
                Predicate statusPredicate = cb.equal(root.get("result"), bo.getResult());
                predicates.add(statusPredicate);
            }

            if(StringUtils.isNoneBlank(bo.getIdentifier())) {
                Predicate statusPredicate = cb.equal(root.get("identifier"), bo.getIdentifier());
                predicates.add(statusPredicate);
            }

            if(bo.getType() != null) {
                Predicate statusPredicate = cb.equal(root.get("type"), bo.getType());
                predicates.add(statusPredicate);
            }
            
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public SmsRecord save(SmsRecord smsRecord) {
        TbSmsRecord tbSmsRecord = MapstructUtils.convert(smsRecord, TbSmsRecord.class);
        assert tbSmsRecord != null;
        TbSmsRecord saved = baseRepository.saveAndFlush(tbSmsRecord);
        return MapstructUtils.convert(saved, SmsRecord.class);
    }
}