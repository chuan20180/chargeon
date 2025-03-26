package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbLedgerSettleDealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LedgerSettleDealerRepository extends JpaRepository<TbLedgerSettleDealer, String>, QuerydslPredicateExecutor<TbLedgerSettleDealer>, JpaSpecificationExecutor<TbLedgerSettleDealer> {

}
