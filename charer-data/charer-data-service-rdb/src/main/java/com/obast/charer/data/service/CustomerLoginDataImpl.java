package com.obast.charer.data.service;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.ICustomerLoginData;
import com.obast.charer.data.dao.CustomerLoginRepository;
import com.obast.charer.data.model.TbCustomerLogin;
import com.obast.charer.data.model.TbStation;
import com.obast.charer.enums.AppOsEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.customer.CustomerLogin;
import com.obast.charer.model.station.Station;
import com.obast.charer.qo.CustomerLoginQueryBo;
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

@Primary
@Service
@RequiredArgsConstructor
public class CustomerLoginDataImpl extends AbstractCommonData<CustomerLoginQueryBo>
        implements ICustomerLoginData, IJPACommData<CustomerLogin, String>, IJPACommonData<CustomerLogin, CustomerLoginQueryBo, String> {


    private final CustomerLoginRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbCustomerLogin.class;
    }

    @Override
    public Class<?> getTClass() {
        return CustomerLogin.class;
    }

    @Override
    public Paging<CustomerLogin> findPage(PageRequest<CustomerLoginQueryBo> request) {
        Specification<TbCustomerLogin> specification = buildSpecification(request.getData());
        Page<TbCustomerLogin> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbCustomerLogin> list = page.getContent();
        List<CustomerLogin> newList = new ArrayList<>();

        for(TbCustomerLogin tbCustomerLogin: list) {
            newList.add(fillData(tbCustomerLogin));
        }

        long total = page.getTotalElements();
        return new Paging<>(total, newList);
    }

    @Override
    public List<CustomerLogin> findList(CustomerLoginQueryBo queryBo) {
        Specification<TbCustomerLogin> specification = buildSpecification(queryBo);
        List<TbCustomerLogin> list = baseRepository.findAll(specification);
        List<CustomerLogin> newList = new ArrayList<>();
        for(TbCustomerLogin tbCustomer: list) {
            newList.add(fillData(tbCustomer));
        }
        return newList;
    }

    @Override
    public CustomerLogin findByCustomerIdAndPlatform(String customerId, PlatformTypeEnum platform) {
        Specification<TbCustomerLogin> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate typePredicate = cb.equal(root.get("platform"), platform);
            predicates.add(typePredicate);

            Predicate customerIdPredicate = cb.equal(root.get("customerId"), customerId);
            predicates.add(customerIdPredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbCustomerLogin entity = baseRepository.findOne(specification).orElse(null);
        return fillData(entity);
    }

    @Override
    public CustomerLogin findByDnAndPlatform(String dn, PlatformTypeEnum platform) {
        Specification<TbCustomerLogin> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate typePredicate = cb.equal(root.get("platform"), platform);
            predicates.add(typePredicate);

            Predicate customerIdPredicate = cb.equal(root.get("dn"), dn);
            predicates.add(customerIdPredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbCustomerLogin entity = baseRepository.findOne(specification).orElse(null);
        return fillData(entity);
    }

    @Override
    public List<CustomerLogin> findListByPlatform(AppOsEnum platform) {
        Specification<TbCustomerLogin> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate typePredicate = cb.equal(root.get("platform"), platform);
            predicates.add(typePredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbCustomerLogin> list = baseRepository.findAll(specification);
        List<CustomerLogin> newList = new ArrayList<>();
        for(TbCustomerLogin tbCustomerLogin: list) {
            newList.add(fillData(tbCustomerLogin));
        }
        return newList;
    }

    @Override
    public List<CustomerLogin> findListByPlatforms(List<AppOsEnum> platforms) {
        Specification<TbCustomerLogin> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate typePredicate = root.get("platform").in(platforms);
            predicates.add(typePredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbCustomerLogin> list = baseRepository.findAll(specification);
        List<CustomerLogin> newList = new ArrayList<>();
        for(TbCustomerLogin tbCustomerLogin: list) {
            newList.add(fillData(tbCustomerLogin));
        }
        return newList;
    }

    private CustomerLogin fillData(TbCustomerLogin tbCustomerLogin) {
        return MapstructUtils.convert(tbCustomerLogin, CustomerLogin.class);
    }


    @Transactional
    @Override
    public CustomerLogin add(CustomerLogin customerLogin) {
        TbCustomerLogin bo = customerLogin.to(TbCustomerLogin.class);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public CustomerLogin update(CustomerLogin customerLogin) {
        TbCustomerLogin bo = baseRepository.findById(customerLogin.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(customerLogin, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }


    public Specification<TbCustomerLogin> buildSpecification(CustomerLoginQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getCustomerId())) {
                Predicate predicate = cb.equal(root.get("customerId"), bo.getCustomerId());
                predicates.add(predicate);
            }
            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("type"), bo.getType().getCode());
                predicates.add(predicate);
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
