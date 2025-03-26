package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CouponRepository extends JpaRepository<TbCoupon, String>, QuerydslPredicateExecutor<TbCoupon>, JpaSpecificationExecutor<TbCoupon> {

}
