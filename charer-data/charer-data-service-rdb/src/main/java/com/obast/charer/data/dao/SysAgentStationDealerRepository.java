package com.obast.charer.data.dao;


import com.obast.charer.data.model.TbSysAgentStationDealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysAgentStationDealerRepository extends JpaRepository<TbSysAgentStationDealer, String>, QuerydslPredicateExecutor<TbSysAgentStationDealer>, JpaSpecificationExecutor<TbSysAgentStationDealer> {

}
