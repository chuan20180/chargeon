package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysLoginInfoRepository;
import com.obast.charer.data.model.TbSysLoginInfo;
import com.obast.charer.data.system.ISysLoginInfoData;
import com.obast.charer.model.system.SysLoginInfo;
import com.obast.charer.qo.SysLoginInfoQueryBo;
import lombok.RequiredArgsConstructor;
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
public class SysLoginInfoDataImpl extends AbstractCommonData<SysLoginInfoQueryBo>
        implements ISysLoginInfoData, IJPACommData<SysLoginInfo, String>, IJPACommonData<SysLoginInfo, SysLoginInfoQueryBo, String> {

    private final SysLoginInfoRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysLoginInfo.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysLoginInfo.class;
    }

    @Override
    public Paging<SysLoginInfo> findPage(PageRequest<SysLoginInfoQueryBo> request) {
        Specification<TbSysLoginInfo> specification = buildSpecification(request.getData());
        Page<TbSysLoginInfo> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysLoginInfo> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysLoginInfo.class));
    }

    @Override
    public List<SysLoginInfo> findList(SysLoginInfoQueryBo queryBo) {
        Specification<TbSysLoginInfo> specification = buildSpecification(queryBo);
        List<TbSysLoginInfo> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysLoginInfo.class);
    }

    @Override
    public List<SysLoginInfo> findAllByTenantId(String id) {
        return MapstructUtils.convert(baseRepository.findByTenantId(id), SysLoginInfo.class);
    }

    @Override
    public List<SysLoginInfo> findAllByAgentId(String id) {
        return MapstructUtils.convert(baseRepository.findByTenantId(id), SysLoginInfo.class);
    }

    @Override
    public List<SysLoginInfo> findAllByDealerId(String id) {
        return MapstructUtils.convert(baseRepository.findByDealerId(id), SysLoginInfo.class);
    }

    public Specification<TbSysLoginInfo> buildSpecification(SysLoginInfoQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
