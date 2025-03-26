package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IAdsData;
import com.obast.charer.data.dao.SysOssRepository;
import com.obast.charer.data.model.TbAds;
import com.obast.charer.data.model.TbSysOss;
import com.obast.charer.data.system.ISysOssData;
import com.obast.charer.model.ads.Ads;
import com.obast.charer.model.system.SysOss;
import com.obast.charer.qo.AdsQueryBo;
import com.obast.charer.qo.SysOssQueryBo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysOssDataImpl  extends AbstractCommonData<SysOssQueryBo>
        implements ISysOssData, IJPACommData<SysOss, String>, IJPACommonData<SysOss, SysOssQueryBo, String> {

    @Autowired
    private SysOssRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysOss.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysOss.class;
    }

    @Override
    public Paging<SysOss> findPage(PageRequest<SysOssQueryBo> request) {
        Specification<TbSysOss> specification = buildSpecification(request.getData());
        Page<TbSysOss> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysOss> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysOss.class));
    }

    @Override
    public List<SysOss> findList(SysOssQueryBo queryBo) {
        Specification<TbSysOss> specification = buildSpecification(queryBo);
        List<TbSysOss> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysOss.class);
    }

    public Specification<TbSysOss> buildSpecification(SysOssQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }


}
