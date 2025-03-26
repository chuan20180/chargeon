package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysUserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysUserPostRepository extends JpaRepository<TbSysUserPost, String>, QuerydslPredicateExecutor<TbSysUserPost> {

    int deleteAllByUserId(String userId);
}
