package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.CustomerNotifyRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.ICustomerNotifyData;
import com.obast.charer.data.model.TbCustomerNotify;
import com.obast.charer.model.customer.CustomerNotify;
import com.obast.charer.qo.CustomerNotifyQueryBo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：客户消息服务实现
 */
@Primary
@Service
@RequiredArgsConstructor
public class CustomerNotifyDataImpl extends AbstractCommonData<CustomerNotifyQueryBo>
        implements ICustomerNotifyData, IJPACommData<CustomerNotify, String>, IJPACommonData<CustomerNotify, CustomerNotifyQueryBo, String> {

    private final CustomerNotifyRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbCustomerNotify.class;
    }

    @Override
    public Class<?> getTClass() {
        return CustomerNotify.class;
    }

    @Override
    public Paging<CustomerNotify> findPage(PageRequest<CustomerNotifyQueryBo> request) {
        Specification<TbCustomerNotify> specification = buildSpecification(request.getData());
        Page<TbCustomerNotify> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbCustomerNotify> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, CustomerNotify.class));
    }

    @Override
    public List<CustomerNotify> findList(CustomerNotifyQueryBo queryBo) {
        Specification<TbCustomerNotify> specification = buildSpecification(queryBo);
        List<TbCustomerNotify> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, CustomerNotify.class);
    }

    @Override
    public List<CustomerNotify> findByCustomerId(String customerId) {
        CustomerNotifyQueryBo bo = new CustomerNotifyQueryBo();

        Specification<TbCustomerNotify> specification = buildSpecification(bo);
        List<TbCustomerNotify> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, CustomerNotify.class);
    }

    @Transactional
    @Override
    public CustomerNotify add(CustomerNotify customerNotify) {
        return MapstructUtils.convert(customerNotify, CustomerNotify.class);
    }

    @Transactional
    @Override
    public void deleteById(String customerNotifyId) {
        baseRepository.deleteById(customerNotifyId);
    }

    public Specification<TbCustomerNotify> buildSpecification(CustomerNotifyQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getId())) {
                Predicate predicate = cb.equal(root.get("id"), bo.getId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getCustomerId())) {
                Predicate predicate = cb.equal(root.get("customerId"), bo.getCustomerId());
                predicates.add(predicate);
            }

            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("type"), bo.getType().getCode());
                predicates.add(predicate);
            }

            if(bo.getScope() != null) {
                Predicate predicate = cb.equal(root.get("scope"), bo.getScope().getCode());
                predicates.add(predicate);
            }

            if(bo.getState() != null) {
                Predicate predicate = cb.equal(root.get("state"), bo.getState().getCode());
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}