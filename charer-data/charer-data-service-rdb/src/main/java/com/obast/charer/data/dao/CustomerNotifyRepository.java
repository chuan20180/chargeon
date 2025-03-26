package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbCustomerNotify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：客户消息DAO接口
 */
public interface CustomerNotifyRepository extends JpaRepository<TbCustomerNotify, String>, QuerydslPredicateExecutor<TbCustomerNotify>, JpaSpecificationExecutor<TbCustomerNotify> {

    List<TbCustomerNotify> findByCustomerId(String customerId);

}
