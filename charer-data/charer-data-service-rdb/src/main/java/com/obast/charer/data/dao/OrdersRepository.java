package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface OrdersRepository extends JpaRepository<TbOrders, String>, QuerydslPredicateExecutor<TbOrders>, JpaSpecificationExecutor<TbOrders> {

    @Query(value = "SELECT count(*) AS order_count, cast(ifnull(sum(b.amount), 0) as DECIMAL(10,2)) AS order_amount, cast(ifnull(sum(b.settled_amount), 0) as DECIMAL(10,2)) AS order_settled_amount, cast(ifnull(sum(b.settled_elec_amount), 0) as DECIMAL(10,2)) AS order_elec_amount, cast(ifnull(sum( b.settled_service_amount), 0) as DECIMAL(10,2)) AS order_service_amount, cast(ifnull(sum( b.settled_park_amount), 0) as DECIMAL(10,2)) AS order_park_amount FROM orders a LEFT JOIN ( SELECT order_id, sum( amount ) AS amount, sum( discount_amount ) AS discount_amount, sum( settled_amount ) AS settled_amount, sum( IF ( type = 1, settled_amount, NULL )) AS settled_elec_amount, sum( IF ( type = 2, settled_amount, NULL )) AS settled_service_amount, sum( IF ( type = 3, settled_amount, NULL )) AS settled_park_amount FROM orders_settle GROUP BY order_id ) b ON a.id = b.order_id WHERE a.state = 10 ", nativeQuery = true)
    Map<String,Object> findSummary();


    @Query(value = "SELECT count(*) AS order_count, cast(ifnull(sum(b.amount), 0) as DECIMAL(10,2)) AS order_amount, cast(ifnull(sum(b.settled_amount), 0) as DECIMAL(10,2)) AS order_settled_amount, cast(ifnull(sum(b.settled_elec_amount), 0) as DECIMAL(10,2)) AS order_elec_amount, cast(ifnull(sum(b.settled_service_amount), 0) as DECIMAL(10,2)) AS order_service_amount, cast(ifnull(sum(b.settled_park_amount), 0) as DECIMAL(10,2)) AS order_park_amount FROM orders a LEFT JOIN ( SELECT order_id, sum( amount ) AS amount, sum( discount_amount ) AS discount_amount, sum( settled_amount ) AS settled_amount, sum( IF ( type = 1, settled_amount, NULL )) AS settled_elec_amount, sum( IF ( type = 2, settled_amount, NULL )) AS settled_service_amount, sum( IF ( type = 3, settled_amount, NULL )) AS settled_park_amount FROM orders_settle GROUP BY order_id ) b ON a.id = b.order_id WHERE a.state = 10 and a.create_time between :startTime and :endTime", nativeQuery = true)
    Map<String,Object> findSummaryByCreateTime(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
