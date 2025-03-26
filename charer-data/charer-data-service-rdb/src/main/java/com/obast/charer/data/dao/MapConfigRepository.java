package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbMapConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MapConfigRepository extends JpaRepository<TbMapConfig, String>, QuerydslPredicateExecutor<TbMapConfig>, JpaSpecificationExecutor<TbMapConfig> {

}
