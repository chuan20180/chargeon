package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface CustomerRepository extends JpaRepository<TbCustomer, String>, QuerydslPredicateExecutor<TbCustomer>, JpaSpecificationExecutor<TbCustomer> {

}
