package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IPluginInfoData;
import com.obast.charer.data.dao.PluginInfoRepository;
import com.obast.charer.data.model.TbPluginInfo;
import com.obast.charer.model.plugin.PluginInfo;
import com.obast.charer.qo.PluginInfoQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;



/**
 * @author sjg
 */
@Primary
@Service
public class PluginInfoDataImpl extends AbstractCommonData<PluginInfoQueryBo>
        implements IPluginInfoData, IJPACommData<PluginInfo, String>, IJPACommonData<PluginInfo, PluginInfoQueryBo, String> {

    @Autowired
    private PluginInfoRepository baseRepository;


    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbPluginInfo.class;
    }

    @Override
    public Class<?> getTClass() {
        return PluginInfo.class;
    }

    @Override
    public Paging<PluginInfo> findPage(PageRequest<PluginInfoQueryBo> request) {
        Specification<TbPluginInfo> specification = buildSpecification(request.getData());
        Page<TbPluginInfo> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbPluginInfo> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, PluginInfo.class));
    }

    @Override
    public List<PluginInfo> findList(PluginInfoQueryBo queryBo) {
        Specification<TbPluginInfo> specification = buildSpecification(queryBo);
        List<TbPluginInfo> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, PluginInfo.class);
    }

    @Override
    public PluginInfo findByPluginId(String pluginId) {
        return MapstructUtils.convert(baseRepository.findByPluginId(pluginId), PluginInfo.class);
    }

    public Specification<TbPluginInfo> buildSpecification(PluginInfoQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

}
