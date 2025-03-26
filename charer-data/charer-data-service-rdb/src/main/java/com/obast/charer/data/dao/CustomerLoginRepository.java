package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbCustomerLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CustomerLoginRepository extends JpaRepository<TbCustomerLogin, String>, QuerydslPredicateExecutor<TbCustomerLogin>, JpaSpecificationExecutor<TbCustomerLogin> {

}
