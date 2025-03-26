package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbCouponCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CouponCodeRepository extends JpaRepository<TbCouponCode, String>, QuerydslPredicateExecutor<TbCouponCode>, JpaSpecificationExecutor<TbCouponCode> {

}
