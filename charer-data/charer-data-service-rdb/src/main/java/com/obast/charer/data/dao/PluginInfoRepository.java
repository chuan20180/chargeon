package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbPluginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author sjg
 */
public interface PluginInfoRepository extends JpaRepository<TbPluginInfo, String>, QuerydslPredicateExecutor<TbPluginInfo>, JpaSpecificationExecutor<TbPluginInfo> {

    TbPluginInfo findByPluginId(String pluginId);

}
