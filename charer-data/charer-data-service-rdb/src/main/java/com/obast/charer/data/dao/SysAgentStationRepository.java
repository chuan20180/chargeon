package com.obast.charer.data.dao;


import com.obast.charer.data.model.TbSysAgentStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysAgentStationRepository extends JpaRepository<TbSysAgentStation, String>, QuerydslPredicateExecutor<TbSysAgentStation>, JpaSpecificationExecutor<TbSysAgentStation> {

}
