package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface SysUserRepository extends JpaRepository<TbSysUser, String>, QuerydslPredicateExecutor<TbSysUser>, JpaSpecificationExecutor<TbSysUser> {

    List<TbSysUser> findByTenantId(String id);

    List<TbSysUser> findByAgentId(String id);

    List<TbSysUser> findByDealerId(String id);
}
