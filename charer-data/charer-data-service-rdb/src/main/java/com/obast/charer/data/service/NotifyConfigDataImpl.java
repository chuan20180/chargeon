package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.NotifyConfigRepository;
import com.obast.charer.data.business.INotifyConfigData;
import com.obast.charer.data.model.TbNotifyConfig;
import com.obast.charer.enums.NotifyIdentifierEnum;
import com.obast.charer.model.notify.NotifyConfig;
import com.obast.charer.qo.NotifyConfigQueryBo;
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
public class NotifyConfigDataImpl  extends AbstractCommonData<NotifyConfigQueryBo>
        implements INotifyConfigData, IJPACommData<NotifyConfig, String>, IJPACommonData<NotifyConfig, NotifyConfigQueryBo, String> {

    @Resource
    private NotifyConfigRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() { return baseRepository; }

    @Override
    public Class<?> getJpaRepositoryClass() {  return TbNotifyConfig.class; }

    @Override
    public Class<?> getTClass() { return NotifyConfig.class; }

    @Override
    public Paging<NotifyConfig> findPage(PageRequest<NotifyConfigQueryBo> request) {
        Specification<TbNotifyConfig> specification = buildSpecification(request.getData());
        Page<TbNotifyConfig> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbNotifyConfig> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, NotifyConfig.class));
    }

    @Override
    public List<NotifyConfig> findList(NotifyConfigQueryBo queryBo) {
        Specification<TbNotifyConfig> specification = buildSpecification(queryBo);
        List<TbNotifyConfig> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, NotifyConfig.class);
    }

    @Override
    public NotifyConfig findByIdentifier(NotifyIdentifierEnum identifierEnum) {
        Specification<TbNotifyConfig> specification = (root, query, cb) -> {
            Predicate predicate = cb.equal(root.get("identifier"), identifierEnum);
            return query.where(predicate).getRestriction();
        };
        List<TbNotifyConfig> list = baseRepository.findAll(specification);
        if(list.isEmpty()) {
            return null;
        }
        return MapstructUtils.convert(list.get(0), NotifyConfig.class);
    }

    @Override
    public List<String> findWechatTemplateIds() {
        List<TbNotifyConfig> list = baseRepository.findAll();
        List<String> templateIds = new ArrayList<>();
        for(TbNotifyConfig config: list) {
            NotifyConfig.Properties properties = config.getProperties();
            if(properties != null) {
                if(StringUtils.isNoneBlank(properties.getPushWechatTemplateId() )) {
                    templateIds.add(properties.getPushWechatTemplateId());
                }
            }
        }

        return templateIds;
    }

    public Specification<TbNotifyConfig> buildSpecification(NotifyConfigQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getName())) {
                Predicate predicate = cb.equal(root.get("name"), bo.getName());
                predicates.add(predicate);
            }

            if(bo.getIdentifier() != null) {
                Predicate predicate = cb.equal(root.get("identifier"), bo.getIdentifier().getData());
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
