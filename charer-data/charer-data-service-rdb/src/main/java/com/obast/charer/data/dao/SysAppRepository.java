package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * 应用信息对象 SYS_APP
 *
 * @author tfd
 * @date 2023-08-10
 */
public interface SysAppRepository extends JpaRepository<TbSysApp, String>, QuerydslPredicateExecutor<TbSysApp>, JpaSpecificationExecutor<TbSysApp> {

}
