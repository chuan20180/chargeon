package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbRefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RefundRepository extends JpaRepository<TbRefund, String>, QuerydslPredicateExecutor<TbRefund>, JpaSpecificationExecutor<TbRefund> {

}
