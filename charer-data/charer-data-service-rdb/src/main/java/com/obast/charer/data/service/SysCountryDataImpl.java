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
import com.obast.charer.data.business.ISysCountryData;
import com.obast.charer.data.dao.SysCountryRepository;
import com.obast.charer.data.model.TbSysCountry;
import com.obast.charer.model.system.SysCountry;
import com.obast.charer.qo.SysCountryQueryBo;
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
public class SysCountryDataImpl extends AbstractCommonData<SysCountryQueryBo>
        implements ISysCountryData, IJPACommData<SysCountry, String>, IJPACommonData<SysCountry, SysCountryQueryBo, String> {

    private final SysCountryRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysCountry.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysCountry.class;
    }

    @Override
    public Paging<SysCountry> findPage(PageRequest<SysCountryQueryBo> request) {
        Specification<TbSysCountry> specification = buildSpecification(request.getData());
        Page<TbSysCountry> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysCountry> list = page.getContent();
        long total = page.getTotalElements();

        List<SysCountry> newList = new ArrayList<>();
        for(TbSysCountry tbSysCountry: list) {
            newList.add(fillData(tbSysCountry));
        }
        return new Paging<>(total, newList);
    }

    @Override
    public List<SysCountry> findList(SysCountryQueryBo queryBo) {
        Specification<TbSysCountry> specification = buildSpecification(queryBo);
        List<TbSysCountry> list = baseRepository.findAll(specification);
        List<SysCountry> newList = new ArrayList<>();
        for(TbSysCountry tbSysCountry: list) {
            newList.add(fillData(tbSysCountry));
        }
        return newList;
    }

    @Transactional
    @Override
    public SysCountry add(SysCountry sysCountry) {
        TbSysCountry bo = new TbSysCountry();
        ReflectUtil.copyNoNulls(sysCountry, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public SysCountry update(SysCountry sysCountry) {
        TbSysCountry bo = null;
        if (StringUtils.isNotBlank(sysCountry.getId())) {
            bo = baseRepository.findById(sysCountry.getId()).orElse(null);
        }
        if (bo == null) {
            throw new BizException(ErrCode.SYS_COUNTRY_NOT_FOUND);
        }
        ReflectUtil.copyNoNulls(sysCountry, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private SysCountry fillData(TbSysCountry tbSysCountry) {
        return MapstructUtils.convert(tbSysCountry, SysCountry.class);
    }


    public Specification<TbSysCountry> buildSpecification(SysCountryQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            //name
            if(StringUtils.isNoneBlank(bo.getName())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("name"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getName() + "%");
                predicates.add(namePredicate);
            }

            if(StringUtils.isNoneBlank(bo.getIso2())) {
                Predicate statusPredicate = cb.equal(root.get("ios2"), bo.getIso2());
                predicates.add(statusPredicate);
            }

            if(StringUtils.isNoneBlank(bo.getIso3())) {
                Predicate statusPredicate = cb.equal(root.get("ios3"), bo.getIso3());
                predicates.add(statusPredicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
