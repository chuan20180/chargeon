package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysRoleRepository extends JpaRepository<TbSysRole, String>, QuerydslPredicateExecutor<TbSysRole>, JpaSpecificationExecutor<TbSysRole> {


}
