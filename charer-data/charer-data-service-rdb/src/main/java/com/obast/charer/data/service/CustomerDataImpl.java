package com.obast.charer.data.service;

import com.github.yitter.idgen.YitIdHelper;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.data.dao.CustomerLoginRepository;
import com.obast.charer.data.dao.CustomerRepository;
import com.obast.charer.data.model.TbCustomer;
import com.obast.charer.data.model.TbCustomerLogin;
import com.obast.charer.enums.CustomerTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.qo.CustomerQueryBo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class CustomerDataImpl extends AbstractCommonData<CustomerQueryBo>
        implements ICustomerData, IJPACommData<Customer, String>, IJPACommonData<Customer, CustomerQueryBo, String> {

    private final CustomerRepository baseRepository;

    private final CustomerLoginRepository customerLoginRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbCustomer.class;
    }

    @Override
    public Class<?> getTClass() {
        return Customer.class;
    }

    @Override
    public Paging<Customer> findPage(PageRequest<CustomerQueryBo> request) {
        Specification<TbCustomer> specification = buildSpecification(request.getData());
        Page<TbCustomer> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbCustomer> list = page.getContent();
        List<Customer> newList = new ArrayList<>();

        for(TbCustomer tbCustomer: list) {
            newList.add(fillData(tbCustomer));
        }

        long total = page.getTotalElements();
        return new Paging<>(total, newList);
    }

    @Override
    public List<Customer> findList(CustomerQueryBo queryBo) {
        Specification<TbCustomer> specification = buildSpecification(queryBo);
        List<TbCustomer> list = baseRepository.findAll(specification);
        List<Customer> newList = new ArrayList<>();
        for(TbCustomer tbCustomer: list) {
            newList.add(fillData(tbCustomer));
        }
        return newList;
    }

    @Override
    public List<Customer> findListByType(CustomerTypeEnum typeEnum) {
        Specification<TbCustomer> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate typePredicate = cb.equal(root.get("type"), typeEnum.getCode());
            predicates.add(typePredicate);
            Predicate statusPredicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
            predicates.add(statusPredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbCustomer> list = baseRepository.findAll(specification);
        List<Customer> newList = new ArrayList<>();
        for(TbCustomer tbCustomer: list) {
            newList.add(fillData(tbCustomer));
        }
        return newList;
    }

    @Override
    public Customer findById(String id) {
        Specification<TbCustomer> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("id"), id);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbCustomer entity = baseRepository.findOne(specification).orElse(null);
        return fillData(entity);
    }

    @Override
    public Customer findByOpenId(String openId) {
        Specification<TbCustomerLogin> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("dn"), openId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbCustomerLogin customerLogin = customerLoginRepository.findOne(specification).orElse(null);
        if(customerLogin == null) {
            return null;
        }

        TbCustomer entity = baseRepository.findById(customerLogin.getCustomerId()).orElse(null);
        return fillData(entity);
    }

    @Override
    public Customer findByUserName(String userName) {
        CustomerQueryBo bo = new CustomerQueryBo();
        bo.setUserName(userName);
        Specification<TbCustomer> specification = buildSpecification(bo);
        TbCustomer entity = baseRepository.findOne(specification).orElse(null);
        return fillData(entity);
    }

    @Override
    public Customer findByMobile(String mobile) {
        CustomerQueryBo bo = new CustomerQueryBo();
        bo.setMobile(mobile);
        Specification<TbCustomer> specification = buildSpecification(bo);
        TbCustomer entity = baseRepository.findOne(specification).orElse(null);
        return fillData(entity);
    }

    @Override
    public boolean checkUserNameUnique(CustomerQueryBo bo) {
        Specification<TbCustomer> specification = buildSpecification(bo);
       return baseRepository.exists(specification);
    }

    @Override
    public Customer add(Customer customer) {
        customer.setLogicalCardNo(generateLogicalCardNo());
        customer.setPhysicalCardNo(generatePhysicalCardNo());
        TbCustomer tbCustomer = MapstructUtils.convert(customer, TbCustomer.class);
        if(tbCustomer != null) {
            TbCustomer entity = baseRepository.saveAndFlush(tbCustomer);
            return fillData(entity);
        }
        return null;
    }

    @Transactional
    @Override
    public Customer update(Customer customer) {
        TbCustomer bo = baseRepository.findById(customer.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(customer, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private Customer fillData(TbCustomer tbCustomer) {
        return MapstructUtils.convert(tbCustomer, Customer.class);
    }

    public Specification<TbCustomer> buildSpecification(CustomerQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();


            if(StringUtils.isNoneBlank(bo.getUserName())) {
                Predicate predicate = cb.equal(root.get("userName"), bo.getUserName());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getMobile())) {
                Predicate predicate = cb.equal(root.get("mobile"), bo.getMobile());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getNickName())) {
                Predicate predicate = cb.equal(root.get("nickName"), bo.getNickName());
                predicates.add(predicate);
            }

            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("type"), bo.getType().getCode());
                predicates.add(predicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }


    private String generateLogicalCardNo() {
        int length = 16;
        Long generatedId = YitIdHelper.nextId();
        int offset = String.valueOf(generatedId).length() - length;
        String id;
        if(offset > 0) {
            double doubleId = generatedId/(Math.pow(10, offset ));
            id = String.valueOf((long) doubleId);
        } else {
            SecureRandom random = new SecureRandom();
            int pow = (int) Math.pow(10, -offset - 1); // 计算随机数的最小值
            int suffix = random.nextInt(9*pow) + pow;
            id = generatedId + String.valueOf(suffix);
        }
        return id;
    }

    private String generatePhysicalCardNo() {
        int length = 16;
        Long generatedId = YitIdHelper.nextId();
        int offset = String.valueOf(generatedId).length() - length;
        String id;
        if(offset > 0) {
            double doubleId = generatedId/(Math.pow(10, offset ));
            id = String.valueOf((long) doubleId);
        } else {
            SecureRandom random = new SecureRandom();
            int pow = (int) Math.pow(10, -offset - 1); // 计算随机数的最小值
            int suffix = random.nextInt(9*pow) + pow;
            id = generatedId + String.valueOf(suffix);
        }
        return id;
    }
}
