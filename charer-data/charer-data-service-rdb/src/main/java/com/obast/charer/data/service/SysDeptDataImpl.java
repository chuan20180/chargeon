package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.SysDeptRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.model.TbSysDept;
import com.obast.charer.data.system.ISysDeptData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysDept;
import com.obast.charer.qo.SysDeptQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Primary
@Service
@RequiredArgsConstructor
public class SysDeptDataImpl extends AbstractCommonData<SysDeptQueryBo>
        implements ISysDeptData, IJPACommData<SysDept, String>, IJPACommonData<SysDept, SysDeptQueryBo, String> {

    @Autowired
    private SysDeptRepository baseRepository;



    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysDept.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysDept.class;
    }


    @Override
    public Paging<SysDept> findPage(PageRequest<SysDeptQueryBo> pageRequest) {
        Specification<TbSysDept> specification = buildSpecification(pageRequest.getData());
        Page<TbSysDept> page = baseRepository.findAll(specification, processPageSort(pageRequest));
        List<TbSysDept> list = page.getContent();
        long total = page.getTotalElements();

        List<SysDept> newList = new ArrayList<>();
        for(TbSysDept tbSysDept: list) {
            newList.add(fillData(tbSysDept));
        }
        return new Paging<>(total, newList);
    }

    @Override
    public List<SysDept> findList(SysDeptQueryBo queryBo) {
        Specification<TbSysDept> specification = buildSpecification(queryBo);
        List<TbSysDept> list = baseRepository.findAll(specification);
        List<SysDept> newList = new ArrayList<>();
        for(TbSysDept tbSysDept: list) {
            newList.add(fillData(tbSysDept));
        }
        return newList;
    }

    @Override
    public List<SysDept> findAllByTenantId(String tenantId) {
        Specification<TbSysDept> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("tenantId"), tenantId);
            predicates.add(statusPredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysDept> list = baseRepository.findAll(specification);
        List<SysDept> newList = new ArrayList<>();
        for(TbSysDept tbSysDept: list) {
            newList.add(fillData(tbSysDept));
        }
        return newList;
    }

    @Override
    public List<SysDept> findAllByAgentId(String agentId) {
        Specification<TbSysDept> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("agentId"), agentId);
            predicates.add(statusPredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysDept> list = baseRepository.findAll(specification);
        List<SysDept> newList = new ArrayList<>();
        for(TbSysDept tbSysDept: list) {
            newList.add(fillData(tbSysDept));
        }
        return newList;
    }



    @Transactional
    @Override
    public SysDept add(SysDept sysDept) {
        TbSysDept bo = sysDept.to(TbSysDept.class);
        bo.setStatus(EnableStatusEnum.Enabled);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public SysDept update(SysDept sysUser) {
        TbSysDept bo = sysUser.to(TbSysDept.class);
        if (StringUtils.isBlank(bo.getId())) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private SysDept fillData(TbSysDept tbSysDept) {
        return MapstructUtils.convert(tbSysDept, SysDept.class);
    }

    public Specification<TbSysDept> buildSpecification(SysDeptQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getParentId())) {
                Predicate statusPredicate = cb.equal(root.get("parentId"), bo.getParentId());
                predicates.add(statusPredicate);
            }

            if(StringUtils.isNoneBlank(bo.getDeptName())) {
                Predicate statusPredicate = cb.equal(root.get("deptName"), bo.getDeptName());
                predicates.add(statusPredicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }


    @Override
    public long countByParentId(String parentId) {
        Specification<TbSysDept> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("parentId"), parentId);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return baseRepository.count(specification);
    }

    @Override
    public List<SysDept> findByDeptId(String deptId) {
        return MapstructUtils.convert(baseRepository.findAll().stream().filter(o -> o.getAncestors() != null && o.getAncestors().contains(deptId.toString()))
                .collect(Collectors.toList()), SysDept.class);
    }

    @Override
    public boolean checkDeptNameUnique(String deptName, String parentId, String deptId) {
        Specification<TbSysDept> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("deptName"), deptName);
            predicates.add(statusPredicate);

            Predicate parentIdPredicate = cb.equal(root.get("parentId"), parentId);
            predicates.add(parentIdPredicate);

            if(StringUtils.isNoneBlank(deptId)) {
                Predicate predicate = cb.equal(root.get("deptId"), deptId);
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return baseRepository.count(specification) == 0;
    }

}
