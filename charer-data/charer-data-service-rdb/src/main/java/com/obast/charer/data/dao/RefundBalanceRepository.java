package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbRefundBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface RefundBalanceRepository extends JpaRepository<TbRefundBalance, String>, QuerydslPredicateExecutor<TbRefundBalance>, JpaSpecificationExecutor<TbRefundBalance> {

    @Query(value = "SELECT cast(ifnull(SUM(a.amount), 0) as DECIMAL(10,2)) FROM refund_balance a left join refund b on a.refund_id = b.id WHERE b.customer_id = :customerId and a.state=10", nativeQuery = true)
    BigDecimal findRefundedAmountByCustomerId(@Param("customerId") String customerId);
}
