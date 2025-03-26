package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbNotifyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NotifyConfigRepository extends JpaRepository<TbNotifyConfig, String>, QuerydslPredicateExecutor<TbNotifyConfig>, JpaSpecificationExecutor<TbNotifyConfig> {

}
