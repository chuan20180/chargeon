package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbOrdersSettle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface OrdersSettleRepository extends JpaRepository<TbOrdersSettle, String>, QuerydslPredicateExecutor<TbOrdersSettle>, JpaSpecificationExecutor<TbOrdersSettle> {

    @Query(value = "SELECT cast(ifnull(SUM(o.settled_amount),0) as DECIMAL(10,2)) FROM orders_settle o WHERE o.customer_id = :customerId", nativeQuery = true)
    BigDecimal findAmountByCustomerId(@Param("customerId") String customerId);
}
