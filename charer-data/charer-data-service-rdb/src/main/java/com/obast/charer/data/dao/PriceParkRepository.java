package com.obast.charer.data.dao;

import com.obast.charer.data.model.price.TbPricePark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PriceParkRepository extends JpaRepository<TbPricePark, String>, QuerydslPredicateExecutor<TbPricePark>, JpaSpecificationExecutor<TbPricePark> {

}
