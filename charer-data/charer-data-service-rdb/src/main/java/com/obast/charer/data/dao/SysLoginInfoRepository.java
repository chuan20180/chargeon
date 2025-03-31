package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysLoginInfoRepository extends JpaRepository<TbSysLoginInfo, String>, QuerydslPredicateExecutor<TbSysLoginInfo>, JpaSpecificationExecutor<TbSysLoginInfo> {

}
