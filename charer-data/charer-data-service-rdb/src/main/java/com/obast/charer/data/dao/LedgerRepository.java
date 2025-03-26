package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LedgerRepository extends JpaRepository<TbLedger, String>, QuerydslPredicateExecutor<TbLedger>, JpaSpecificationExecutor<TbLedger> {

}
