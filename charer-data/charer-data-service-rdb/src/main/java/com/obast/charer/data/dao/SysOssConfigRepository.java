package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysOssConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysOssConfigRepository  extends JpaRepository<TbSysOssConfig, String>, QuerydslPredicateExecutor<TbSysOssConfig>, JpaSpecificationExecutor<TbSysOssConfig> {

}
