package com.obast.charer.data.dao;


import com.obast.charer.data.model.TbSysAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysAgentRepository extends JpaRepository<TbSysAgent, String>, QuerydslPredicateExecutor<TbSysAgent>, JpaSpecificationExecutor<TbSysAgent> {

}
