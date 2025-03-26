package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysDictData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysDictDataRepository  extends JpaRepository<TbSysDictData, String>, QuerydslPredicateExecutor<TbSysDictData>, JpaSpecificationExecutor<TbSysDictData> {

}
