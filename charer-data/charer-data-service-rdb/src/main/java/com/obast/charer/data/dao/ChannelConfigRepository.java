package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbChannelConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChannelConfigRepository extends JpaRepository<TbChannelConfig, String>, QuerydslPredicateExecutor<TbChannelConfig>, JpaSpecificationExecutor<TbChannelConfig> {
}
