package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysDeptRepository extends JpaRepository<TbSysDept, String>, QuerydslPredicateExecutor<TbSysDept>, JpaSpecificationExecutor<TbSysDept> {


}
