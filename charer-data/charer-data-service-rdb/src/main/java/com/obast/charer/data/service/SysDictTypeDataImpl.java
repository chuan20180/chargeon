package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysDictTypeRepository;
import com.obast.charer.data.model.TbSysDictType;
import com.obast.charer.data.system.ISysDictTypeData;
import com.obast.charer.model.system.SysDictType;
import com.obast.charer.qo.SysDictTypeQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



/**
 * @Author：tfd
 * @Date：2023/5/30 13:43
 */
@Primary
@Service
@RequiredArgsConstructor
public class SysDictTypeDataImpl extends AbstractCommonData<SysDictTypeQueryBo>
        implements ISysDictTypeData, IJPACommData<SysDictType, String>, IJPACommonData<SysDictType, SysDictTypeQueryBo, String> {

    @Autowired
    private SysDictTypeRepository baseRepository;


    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysDictType.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysDictType.class;
    }

    @Override
    public Paging<SysDictType> findPage(PageRequest<SysDictTypeQueryBo> request) {
        Specification<TbSysDictType> specification = buildSpecification(request.getData());
        Page<TbSysDictType> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysDictType> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysDictType.class));
    }

    @Override
    public List<SysDictType> findList(SysDictTypeQueryBo queryBo) {
        Specification<TbSysDictType> specification = buildSpecification(queryBo);
        List<TbSysDictType> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysDictType.class);
    }

    public Specification<TbSysDictType> buildSpecification(SysDictTypeQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public SysDictType findByDicType(String dictType) {
        return null;
    }

    @Override
    public void updateDicType(String dictType, String newType) {

    }

    @Override
    public boolean checkDictTypeUnique(SysDictType dictType) {

        Specification<TbSysDictType> specification = (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            javax.persistence.criteria.Predicate dictTypePredicate = cb.equal(root.get("dictType"), dictType.getDictType());
            predicates.add(dictTypePredicate);

            if(!StringUtils.isNoneBlank(dictType.getId())) {
                javax.persistence.criteria.Predicate predicate = cb.notEqual(root.get("id"), dictType.getId());
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
        return baseRepository.count(specification) > 0;
    }
}
