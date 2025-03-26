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
import com.obast.charer.data.business.INoticeData;
import com.obast.charer.data.dao.NoticeRepository;
import com.obast.charer.data.model.TbNotice;
import com.obast.charer.data.model.TbRecharge;
import com.obast.charer.enums.NoticeStateEnum;
import com.obast.charer.model.platform.Recharge;
import com.obast.charer.model.system.Notice;
import com.obast.charer.qo.NoticeQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class NoticeDataImpl extends AbstractCommonData<NoticeQueryBo>
        implements INoticeData, IJPACommData<Notice, String>, IJPACommonData<Notice, NoticeQueryBo, String> {

    @Autowired
    private NoticeRepository baseRepository;


    @Override
    public JpaRepository<?,?> getBaseRepository() { return baseRepository; }

    @Override
    public Class<?> getJpaRepositoryClass() { return TbNotice.class; }

    @Override
    public Class<?> getTClass() { return Notice.class; }


    @Override
    public Paging<Notice> findPage(PageRequest<NoticeQueryBo> request) {
        Specification<TbNotice> specification = buildSpecification(request.getData());
        Page<TbNotice> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbNotice> list = page.getContent();
        long total = page.getTotalElements();

        List<Notice> newList = new ArrayList<>();
        for(TbNotice tbNotice: list) {
            newList.add(fillData(tbNotice));
        }
        return new Paging<>(total, newList);
    }

    @Override
    public List<Notice> findList(NoticeQueryBo queryBo) {
        Specification<TbNotice> specification = buildSpecification(queryBo);
        List<TbNotice> list = baseRepository.findAll(specification);
        List<Notice> newList = new ArrayList<>();
        for(TbNotice tbNotice: list) {
            newList.add(fillData(tbNotice));
        }
        return newList;
    }

    @Transactional
    @Override
    public Notice add(Notice station) {
        TbNotice bo = new TbNotice();
        ReflectUtil.copyNoNulls(station, bo);
        bo.setState(NoticeStateEnum.NoPublish);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public Notice update(Notice entity) {
        TbNotice bo = baseRepository.findById(entity.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(entity, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private Notice fillData(TbNotice tbNotice) {
        return MapstructUtils.convert(tbNotice, Notice.class);
    }

    public Specification<TbNotice> buildSpecification(NoticeQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getTitle())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                javax.persistence.criteria.Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("title"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getTitle() + "%");
                predicates.add(namePredicate);
            }

            if(StringUtils.isNoneBlank(bo.getContent())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                javax.persistence.criteria.Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("content"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getContent() + "%");
                predicates.add(namePredicate);
            }

            if(bo.getState() != null) {
                javax.persistence.criteria.Predicate statusPredicate = cb.equal(root.get("state"), bo.getState().getCode());
                predicates.add(statusPredicate);
            }


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
