package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysTenantPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysTenantPackageRepository  extends JpaRepository<TbSysTenantPackage, String>, QuerydslPredicateExecutor<TbSysTenantPackage>, JpaSpecificationExecutor<TbSysTenantPackage> {

}
