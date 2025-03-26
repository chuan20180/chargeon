package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbRecharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RechargeRepository extends JpaRepository<TbRecharge, String>, QuerydslPredicateExecutor<TbRecharge>, JpaSpecificationExecutor<TbRecharge> {

}
