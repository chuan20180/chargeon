package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IChargerDirectiveData;
import com.obast.charer.data.dao.ChargerDirectiveRepository;
import com.obast.charer.data.model.device.TbChargerDirective;
import com.obast.charer.enums.ChargerDirectiveStateEnum;
import com.obast.charer.model.device.ChargerDirective;
import com.obast.charer.qo.ChargerDirectiveQueryBo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class ChargerDirectiveDataImpl extends AbstractCommonData<ChargerDirectiveQueryBo>
        implements IChargerDirectiveData, IJPACommData<ChargerDirective, String>, IJPACommonData<ChargerDirective, ChargerDirectiveQueryBo, String> {

    private final ChargerDirectiveRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbChargerDirective.class;
    }

    @Override
    public Class<?> getTClass() {
        return ChargerDirective.class;
    }


    @Override
    public Paging<ChargerDirective> findPage(PageRequest<ChargerDirectiveQueryBo> request) {
        Specification<TbChargerDirective> specification = buildSpecification(request.getData());
        Page<TbChargerDirective> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbChargerDirective> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, ChargerDirective.class));
    }

    @Override
    public List<ChargerDirective> findList(ChargerDirectiveQueryBo queryBo) {
        Specification<TbChargerDirective> specification = buildSpecification(queryBo);
        List<TbChargerDirective> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, ChargerDirective.class);
    }

    @Override
    public ChargerDirective findOne(ChargerDirectiveQueryBo queryBo) {
        Specification<TbChargerDirective> specification = buildSpecification(queryBo);
        List<TbChargerDirective> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list.stream().findFirst().orElse(null), ChargerDirective.class);
    }

    @Override
    public List<ChargerDirective> findByChargerDn(String dn) {
        Specification<TbChargerDirective> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("dn"), dn);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbChargerDirective> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, ChargerDirective.class);
    }

    /*
     * 生成订单
     */
    @Override
    public ChargerDirective add(ChargerDirective chargerDirective) {
        if(chargerDirective.getSerial() == null) {
            chargerDirective.setSerial(generateSerial(chargerDirective.getDn()));
        }

        chargerDirective.setState(ChargerDirectiveStateEnum.Pending);
        TbChargerDirective tbChargerDirective = MapstructUtils.convert(chargerDirective, TbChargerDirective.class);
        if(tbChargerDirective == null) {
            return null;
        }
        return MapstructUtils.convert(baseRepository.saveAndFlush(tbChargerDirective), ChargerDirective.class);
    }

    public Specification<TbChargerDirective> buildSpecification(ChargerDirectiveQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getId())) {
                Predicate predicate = cb.equal(root.get("id"), bo.getId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getDn())) {
                Predicate predicate = cb.equal(root.get("dn"), bo.getDn());
                predicates.add(predicate);
            }

            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("type"), bo.getType().getCode());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getRelateId())) {
                Predicate predicate = cb.equal(root.get("relateId"), bo.getRelateId());
                predicates.add(predicate);
            }

            if(bo.getSerial() != null) {
                Predicate predicate = cb.equal(root.get("serial"), bo.getSerial());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getDirective())) {
                Predicate predicate = cb.equal(root.get("directive"), bo.getDirective());
                predicates.add(predicate);
            }

            if(bo.getState() != null) {
                Predicate statusPredicate = cb.equal(root.get("state"), bo.getState().getCode());
                predicates.add(statusPredicate);
            }

            if(bo.getResult() != null) {
                Predicate statusPredicate = cb.equal(root.get("result"), bo.getResult().getCode());
                predicates.add(statusPredicate);
            }

            if(StringUtils.isNoneBlank(bo.getReason())) {
                Predicate predicate = cb.equal(root.get("reason"), bo.getReason());
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    //生成序列号
    private Integer generateSerial(String chargerDn) {

        Specification<TbChargerDirective> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate statusPredicate = cb.equal(root.get("dn"), chargerDn);
            predicates.add(statusPredicate);

            Expression<String> dateStringExpr = cb.function("DATE_FORMAT", String.class,  root.get("createTime"), cb.literal("%Y-%m-%d"));
            Predicate datePredicate = cb.equal(cb.lower(dateStringExpr), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            predicates.add(datePredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbChargerDirective entity = baseRepository.findAll(specification).stream().findFirst().orElse(null);

        Integer serial = 0;
        if(entity != null) {
            serial = entity.getSerial();
        }
        serial++;
        return serial;
    }
}
