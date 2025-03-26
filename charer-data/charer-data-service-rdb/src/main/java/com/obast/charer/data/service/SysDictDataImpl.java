/*
 *
 *  * | Licensed 未经许可不能去掉「OPENIITA」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [OPENIITA]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysDictDataRepository;
import com.obast.charer.data.model.TbSysDictData;
import com.obast.charer.data.system.ISysDictData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysDictData;
import com.obast.charer.qo.SysDictDataQueryBo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
public class SysDictDataImpl extends AbstractCommonData<SysDictDataQueryBo>
        implements ISysDictData, IJPACommData<SysDictData, String>, IJPACommonData<SysDictData, SysDictDataQueryBo, String> {

    @Autowired
    private SysDictDataRepository baseRepository;

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysDictData.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysDictData.class;
    }

    @Override
    public Paging<SysDictData> findPage(PageRequest<SysDictDataQueryBo> request) {
        Specification<TbSysDictData> specification = buildSpecification(request.getData());
        Page<TbSysDictData> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysDictData> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysDictData.class));
    }

    @Override
    public List<SysDictData> findList(SysDictDataQueryBo queryBo) {
        Specification<TbSysDictData> specification = buildSpecification(queryBo);
        List<TbSysDictData> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysDictData.class);
    }

    public Specification<TbSysDictData> buildSpecification(SysDictDataQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public List<SysDictData> findByDicType(String dictType) {
        Specification<TbSysDictData> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate statusPredicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
            predicates.add(statusPredicate);

            Predicate dictTypePredicate = cb.equal(root.get("dictType"), dictType);
            predicates.add(dictTypePredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysDictData> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysDictData.class);
    }

    @Override
    public long countByDicType(String dictType) {
        return 0;
    }

}
