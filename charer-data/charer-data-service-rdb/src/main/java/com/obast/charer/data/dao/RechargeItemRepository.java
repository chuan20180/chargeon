package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbRechargeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface RechargeItemRepository extends JpaRepository<TbRechargeItem, String>, QuerydslPredicateExecutor<TbRechargeItem>, JpaSpecificationExecutor<TbRechargeItem> {
    List<TbRechargeItem> findByRechargeId(String name);
}
