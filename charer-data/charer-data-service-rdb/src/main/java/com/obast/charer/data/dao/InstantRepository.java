package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbInstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface InstantRepository extends JpaRepository<TbInstant, String>, QuerydslPredicateExecutor<TbInstant>, JpaSpecificationExecutor<TbInstant> {
    TbInstant findByTranId(String tranId);

}
