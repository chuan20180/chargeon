package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface SysRoleRepository extends JpaRepository<TbSysRole, String>, QuerydslPredicateExecutor<TbSysRole>, JpaSpecificationExecutor<TbSysRole> {


}
