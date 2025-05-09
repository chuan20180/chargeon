package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysConfigRepository extends JpaRepository<TbSysConfig, String>, QuerydslPredicateExecutor<TbSysConfig>, JpaSpecificationExecutor<TbSysConfig> {

}
