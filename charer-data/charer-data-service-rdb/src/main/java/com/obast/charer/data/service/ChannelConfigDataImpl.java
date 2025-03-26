package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.ChannelConfigRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IChannelConfigData;
import com.obast.charer.data.model.TbChannelConfig;
import com.obast.charer.enums.ChannelIdentifierEnum;
import com.obast.charer.model.notify.ChannelConfig;
import com.obast.charer.qo.ChannelConfigQueryBo;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * author: 石恒
 * date: 2023-05-11 17:43
 * description:
 **/
@Primary
@Service
public class ChannelConfigDataImpl  extends AbstractCommonData<ChannelConfigQueryBo>
        implements IChannelConfigData, IJPACommData<ChannelConfig, String>, IJPACommonData<ChannelConfig, ChannelConfigQueryBo, String> {

    @Resource
    private ChannelConfigRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbChannelConfig.class;
    }

    @Override
    public Class<?> getTClass() {
        return ChannelConfig.class;
    }

    @Override
    public Paging<ChannelConfig> findPage(PageRequest<ChannelConfigQueryBo> request) {
        Specification<TbChannelConfig> specification = buildSpecification(request.getData());
        Page<TbChannelConfig> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbChannelConfig> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, ChannelConfig.class));
    }

    @Override
    public List<ChannelConfig> findList(ChannelConfigQueryBo queryBo) {
        Specification<TbChannelConfig> specification = buildSpecification(queryBo);
        List<TbChannelConfig> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, ChannelConfig.class);
    }

    @Override
    public ChannelConfig findByIdentifier(ChannelIdentifierEnum identifierEnum) {
        Specification<TbChannelConfig> specification = (root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("identifier"), identifierEnum);
            return query.where(predicate).getRestriction();
        };
        List<TbChannelConfig> list = baseRepository.findAll(specification);
        if(list.isEmpty()) {
            return null;
        }
        return MapstructUtils.convert(list.get(0), ChannelConfig.class);
    }

    public Specification<TbChannelConfig> buildSpecification(ChannelConfigQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getName())) {
                Predicate predicate = cb.equal(root.get("name"), bo.getName());
                predicates.add(predicate);
            }

            if(bo.getIdentifier() != null) {
                Predicate predicate = cb.equal(root.get("identifier"), bo.getIdentifier().getCode());
                predicates.add(predicate);
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
