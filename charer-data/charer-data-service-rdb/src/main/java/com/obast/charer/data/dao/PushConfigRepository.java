package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbPushConfig;
import com.obast.charer.model.push.PushConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PushConfigRepository extends JpaRepository<TbPushConfig, String>, QuerydslPredicateExecutor<TbPushConfig>, JpaSpecificationExecutor<TbPushConfig> {
    TbPushConfig findByIdentifier(String identifier);
}
