package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysPostRepository  extends JpaRepository<TbSysPost, String>, QuerydslPredicateExecutor<TbSysPost>, JpaSpecificationExecutor<TbSysPost> {

}
