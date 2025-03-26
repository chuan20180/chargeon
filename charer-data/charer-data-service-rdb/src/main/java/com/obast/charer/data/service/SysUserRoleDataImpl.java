package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.SysUserRoleRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.model.TbSysUserRole;
import com.obast.charer.data.system.ISysUserRoleData;
import com.obast.charer.model.system.SysUserRole;
import com.obast.charer.qo.SysUserRoleQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class SysUserRoleDataImpl  extends AbstractCommonData<SysUserRoleQueryBo>
        implements ISysUserRoleData, IJPACommData<SysUserRole, String>, IJPACommonData<SysUserRole, SysUserRoleQueryBo, String> {

    private final SysUserRoleRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysUserRole.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysUserRole.class;
    }

    @Override
    public Paging<SysUserRole> findPage(PageRequest<SysUserRoleQueryBo> request) {
        Specification<TbSysUserRole> specification = buildSpecification(request.getData());
        Page<TbSysUserRole> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysUserRole> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysUserRole.class));
    }

    @Override
    public List<SysUserRole> findList(SysUserRoleQueryBo queryBo) {
        Specification<TbSysUserRole> specification = buildSpecification(queryBo);
        List<TbSysUserRole> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysUserRole.class);
    }

    public Specification<TbSysUserRole> buildSpecification(SysUserRoleQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public int deleteByUserId(String userId) {
        return baseRepository.deleteAllByUserId(userId);
    }

    @Override
    public long countUserRoleByRoleId(String roleId) {
        Specification<TbSysUserRole> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("roleId"), roleId);
            predicates.add(statusPredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return baseRepository.count(specification);
    }

    @Override
    public long insertBatch(List<SysUserRole> list) {
        return baseRepository.saveAll(MapstructUtils.convert(list,TbSysUserRole.class)).size();
    }

    @Override
    public void delete(String roleId, List<String> userIds) {

        Specification<TbSysUserRole> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("roleId"), roleId);
            predicates.add(statusPredicate);

            Predicate userIdsPredicate = root.get("userId").in(userIds);
            predicates.add(userIdsPredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysUserRole> list = baseRepository.findAll(specification);

        baseRepository.deleteAllById(list.stream().map(TbSysUserRole::getId).collect(Collectors.toList()));

    }

}
