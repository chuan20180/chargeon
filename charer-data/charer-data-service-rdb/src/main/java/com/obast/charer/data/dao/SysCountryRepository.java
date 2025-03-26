package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysCountryRepository extends JpaRepository<TbSysCountry, String>, QuerydslPredicateExecutor<TbSysCountry>, JpaSpecificationExecutor<TbSysCountry> {

}
