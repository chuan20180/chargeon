package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysDictType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysDictTypeRepository   extends JpaRepository<TbSysDictType, String>, QuerydslPredicateExecutor<TbSysDictType>, JpaSpecificationExecutor<TbSysDictType> {

}
