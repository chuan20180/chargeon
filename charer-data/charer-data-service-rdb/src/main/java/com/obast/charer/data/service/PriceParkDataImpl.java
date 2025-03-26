package com.obast.charer.data.service;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.PriceParkRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IPriceParkData;
import com.obast.charer.data.model.price.TbPricePark;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.price.PricePark;
import com.obast.charer.qo.PriceParkQueryBo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class PriceParkDataImpl extends AbstractCommonData<PriceParkQueryBo>
        implements IPriceParkData, IJPACommData<PricePark, String>, IJPACommonData<PricePark, PriceParkQueryBo, String> {

    private final PriceParkRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbPricePark.class;
    }

    @Override
    public Class<?> getTClass() {
        return PricePark.class;
    }

    @Override
    public Paging<PricePark> findPage(PageRequest<PriceParkQueryBo> request) {
        Specification<TbPricePark> specification = buildSpecification(request.getData());
        Page<TbPricePark> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbPricePark> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, PricePark.class));
    }

    @Override
    public List<PricePark> findList(PriceParkQueryBo queryBo) {
        Specification<TbPricePark> specification = buildSpecification(queryBo);
        List<TbPricePark> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, PricePark.class);
    }

    @Transactional
    @Override
    public PricePark add(PricePark pricePark) {
        TbPricePark bo = pricePark.to(TbPricePark.class);
        bo.setNo(this.generateNo());
        bo.setStatus(EnableStatusEnum.Enabled);
        TbPricePark tbPricePark = baseRepository.saveAndFlush(bo);
        return MapstructUtils.convert(tbPricePark, PricePark.class);
    }

    @Transactional
    @Override
    public PricePark update(PricePark entity) {
        TbPricePark bo = baseRepository.findById(entity.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(entity, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), PricePark.class);
    }

    public Specification<TbPricePark> buildSpecification(PriceParkQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getName())) {
                Predicate predicate = cb.like(root.get("name"), bo.getName());
                predicates.add(predicate);
            }


            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    private Short generateNo() {
        short no = 0;
        Sort sort = Sort.by(Sort.Order.desc("no"));
        List<TbPricePark> list = baseRepository.findAll(sort);
        if(!list.isEmpty()) {
            no = list.get(0).getNo();
        }
        no++;
        return no;
    }
}
