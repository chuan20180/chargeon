package com.obast.charer.data.dao;

import com.obast.charer.data.model.price.TbPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<TbPrice, String>, QuerydslPredicateExecutor<TbPrice>, JpaSpecificationExecutor<TbPrice> {
    Optional<TbPrice> findByName(String name);
}
