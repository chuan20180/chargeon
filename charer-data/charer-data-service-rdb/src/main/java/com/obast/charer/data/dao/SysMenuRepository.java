package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysMenuRepository extends JpaRepository<TbSysMenu, String>, QuerydslPredicateExecutor<TbSysMenu>, JpaSpecificationExecutor<TbSysMenu> {

}
