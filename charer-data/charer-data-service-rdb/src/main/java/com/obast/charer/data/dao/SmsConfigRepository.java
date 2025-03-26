package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSmsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SmsConfigRepository extends JpaRepository<TbSmsConfig, String>, QuerydslPredicateExecutor<TbSmsConfig>, JpaSpecificationExecutor<TbSmsConfig> {

}
