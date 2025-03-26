package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysUserRoleRepository extends JpaRepository<TbSysUserRole, String>, QuerydslPredicateExecutor<TbSysUserRole>, JpaSpecificationExecutor<TbSysUserRole> {
    int deleteAllByUserId(String userId);
}


