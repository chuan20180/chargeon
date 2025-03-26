package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbTopup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface TopupRepository extends JpaRepository<TbTopup, String>, QuerydslPredicateExecutor<TbTopup>, JpaSpecificationExecutor<TbTopup> {
    TbTopup findByTranId(String tranId);

    @Query(value = "SELECT cast(ifnull(SUM(t.paid_amount), 0) as DECIMAL(10,2)) as paid_amount FROM topup t WHERE state=10", nativeQuery = true)
    Map<String, Object> findSummary();

    @Query(value = "SELECT cast(ifnull(SUM(t.paid_amount), 0) as DECIMAL(10,2)) as paid_amount FROM topup t WHERE state=10 and t.create_time between :startTime and :endTime", nativeQuery = true)
    Map<String, Object> findSummaryByCreateTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Query(value = "SELECT cast(ifnull(SUM(t.arrival_amount), 0) as DECIMAL(10,2)) FROM topup t WHERE t.customer_id = :customerId and state=10", nativeQuery = true)
    BigDecimal findTopupAmountByCustomerId(@Param("customerId") String customerId);
}
