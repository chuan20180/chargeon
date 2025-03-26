package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysOperLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysOperLogRepository  extends JpaRepository<TbSysOperLog, String>, QuerydslPredicateExecutor<TbSysOperLog>, JpaSpecificationExecutor<TbSysOperLog> {

}
