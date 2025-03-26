package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbAlertConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AlertConfigRepository extends JpaRepository<TbAlertConfig, String>, QuerydslPredicateExecutor<TbAlertConfig>, JpaSpecificationExecutor<TbAlertConfig> {

}
