package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.ProtocolRepository;
import com.obast.charer.data.model.TbProtocol;
import com.obast.charer.data.platform.IProtocolData;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.model.protocol.Protocol;
import com.obast.charer.qo.ProtocolQueryBo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
public class ProtocolDataImpl extends AbstractCommonData<ProtocolQueryBo>
        implements IProtocolData, IJPACommData<Protocol, String>, IJPACommonData<Protocol, ProtocolQueryBo, String> {

    @Autowired
    private ProtocolRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbProtocol.class;
    }

    @Override
    public Class<?> getTClass() {
        return Protocol.class;
    }

    @Override
    public Paging<Protocol> findPage(PageRequest<ProtocolQueryBo> request) {
        Specification<TbProtocol> specification = buildSpecification(request.getData());
        Page<TbProtocol> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbProtocol> list = page.getContent();
        List<Protocol> products = new ArrayList<>();
        for (TbProtocol product : list) {
            Protocol entity = fillProtocol(product);
            products.add(entity);
        }
        long total = page.getTotalElements();
        return new Paging<>(total, products);
    }

    @Override
    public List<Protocol> findList(ProtocolQueryBo queryBo) {
        Specification<TbProtocol> specification = buildSpecification(queryBo);
        List<TbProtocol> list = baseRepository.findAll(specification);
        List<Protocol> products = new ArrayList<>();
        for (TbProtocol product : list) {
            Protocol entity = fillProtocol(product);
            products.add(entity);
        }
        return products;
    }

    @Override
    public Protocol findByProtocolKey(String productKey) {
        ProtocolQueryBo bo = new ProtocolQueryBo();
        bo.setProtocolKey(productKey);
        Specification<TbProtocol> specification = buildSpecification(bo);
        TbProtocol entity = baseRepository.findOne(specification).orElse(null);
        return fillProtocol(entity);
    }

    @Override
    public Protocol findById(String id) {
        TbProtocol entity = baseRepository.findById(id).orElse(null);
        return fillProtocol(entity);
    }

    private Protocol fillProtocol(TbProtocol product) {
        if (product == null) {
            return null;
        }

        Protocol entity = MapstructUtils.convert(product, Protocol.class);
        if(entity == null) {
            return null;
        }
        if(product.getType().equals(ProductTypeEnum.Charger)) {
            entity.setCharger(JsonUtils.parseObject(product.getConfig(), Protocol.Charger.class));
        }

        return entity;
    }

    public Specification<TbProtocol> buildSpecification(ProtocolQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(bo.getName() != null) {
                Predicate predicate = cb.like(root.get("name"), bo.getName());
                predicates.add(predicate);
            }

            if(bo.getStatus() != null) {
                Predicate predicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(predicate);
            }

            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("type"), bo.getType());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getProtocolKey())) {
                Predicate statusPredicate = cb.equal(root.get("protocolKey"), bo.getProtocolKey());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
