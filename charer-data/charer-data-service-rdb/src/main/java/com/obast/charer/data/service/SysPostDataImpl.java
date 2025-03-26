package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysPostRepository;
import com.obast.charer.data.model.TbSysPost;
import com.obast.charer.data.system.ISysPostData;
import com.obast.charer.model.system.SysPost;
import com.obast.charer.qo.SysPostQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author：tfd
 * @Date：2023/5/30 18:20
 */
@Primary
@Service
@RequiredArgsConstructor
public class SysPostDataImpl  extends AbstractCommonData<SysPostQueryBo>
        implements ISysPostData, IJPACommData<SysPost, String>, IJPACommonData<SysPost, SysPostQueryBo, String> {

    private final SysPostRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysPost.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysPost.class;
    }

    @Override
    public Paging<SysPost> findPage(PageRequest<SysPostQueryBo> request) {
        Specification<TbSysPost> specification = buildSpecification(request.getData());
        Page<TbSysPost> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysPost> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysPost.class));
    }

    @Override
    public List<SysPost> findList(SysPostQueryBo queryBo) {
        Specification<TbSysPost> specification = buildSpecification(queryBo);
        List<TbSysPost> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysPost.class);
    }

    public Specification<TbSysPost> buildSpecification(SysPostQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public List<String> selectPostListByUserId(String userId) {
        return null;
    }

    @Override
    public List<SysPost> selectPostList(SysPost post) {
        return null;
    }

    @Override
    public boolean checkPostNameUnique(SysPost post) {
        return true;
    }

    @Override
    public boolean checkPostCodeUnique(SysPost post) {
        return true;
    }

}
