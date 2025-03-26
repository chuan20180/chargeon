package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PromotionRepository extends JpaRepository<TbPromotion, String>, QuerydslPredicateExecutor<TbPromotion>, JpaSpecificationExecutor<TbPromotion> {

}
