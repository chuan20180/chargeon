package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<TbProduct, String>, QuerydslPredicateExecutor<TbProduct>, JpaSpecificationExecutor<TbProduct> {
    TbProduct findByProductKey(String productKey);

    List<TbProduct> findByCategory(String category);
}
