package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbAds;
import com.obast.charer.data.model.TbSysOss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysOssRepository  extends JpaRepository<TbSysOss, String>, QuerydslPredicateExecutor<TbSysOss>, JpaSpecificationExecutor<TbSysOss> {

}
