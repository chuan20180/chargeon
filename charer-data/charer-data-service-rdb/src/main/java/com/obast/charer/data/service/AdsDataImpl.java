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
import com.obast.charer.data.business.IAdsData;
import com.obast.charer.data.dao.AdsRepository;
import com.obast.charer.data.model.TbAds;
import com.obast.charer.data.model.TbNotice;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.ads.Ads;
import com.obast.charer.qo.AdsQueryBo;
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


@Primary
@Service
@RequiredArgsConstructor
public class AdsDataImpl extends AbstractCommonData<AdsQueryBo>
        implements IAdsData, IJPACommData<Ads, String>, IJPACommonData<Ads, AdsQueryBo, String> {

    private final AdsRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbAds.class;
    }

    @Override
    public Class<?> getTClass() {
        return Ads.class;
    }

    @Override
    public Paging<Ads> findPage(PageRequest<AdsQueryBo> request) {
        Specification<TbAds> specification = buildSpecification(request.getData());
        Page<TbAds> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbAds> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Ads.class));
    }

    @Override
    public List<Ads> findList(AdsQueryBo queryBo) {
        Specification<TbAds> specification = buildSpecification(queryBo);
        List<TbAds> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Ads.class);
    }

    @Transactional
    @Override
    public Ads add(Ads ads) {
        TbAds bo = new TbAds();
        ReflectUtil.copyNoNulls(ads, bo);
        bo.setStatus(EnableStatusEnum.Enabled);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public Ads update(Ads entity) {
        TbAds bo = baseRepository.findById(entity.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(entity, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private Ads fillData(TbAds tbAds) {
        return MapstructUtils.convert(tbAds, Ads.class);
    }


    public Specification<TbAds> buildSpecification(AdsQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getId())) {
                Predicate predicate = cb.equal(root.get("id"), bo.getId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getName())) {
                Predicate predicate = cb.equal(root.get("name"), bo.getName());
                predicates.add(predicate);
            }

            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("type"), bo.getType().getCode());
                predicates.add(predicate);
            }

            if(bo.getStatus() != null) {
                Predicate predicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
