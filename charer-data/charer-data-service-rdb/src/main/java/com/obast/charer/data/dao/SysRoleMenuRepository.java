package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysRoleMenuRepository extends JpaRepository<TbSysRoleMenu, String>, QuerydslPredicateExecutor<TbSysRoleMenu>, JpaSpecificationExecutor<TbSysRoleMenu> {

}
