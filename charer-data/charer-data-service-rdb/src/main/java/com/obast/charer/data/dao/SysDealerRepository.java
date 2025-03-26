package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysDealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysDealerRepository extends JpaRepository<TbSysDealer, String>, QuerydslPredicateExecutor<TbSysDealer>, JpaSpecificationExecutor<TbSysDealer> {

}
