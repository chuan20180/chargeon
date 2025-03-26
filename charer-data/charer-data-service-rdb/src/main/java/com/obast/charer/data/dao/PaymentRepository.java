package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PaymentRepository extends JpaRepository<TbPayment, String>, QuerydslPredicateExecutor<TbPayment>, JpaSpecificationExecutor<TbPayment> {

}
