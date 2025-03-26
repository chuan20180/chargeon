package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbLedgerSettle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LedgerSettleRepository extends JpaRepository<TbLedgerSettle, String>, QuerydslPredicateExecutor<TbLedgerSettle>, JpaSpecificationExecutor<TbLedgerSettle> {

}
