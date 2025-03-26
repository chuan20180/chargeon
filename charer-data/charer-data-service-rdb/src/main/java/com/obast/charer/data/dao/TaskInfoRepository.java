package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbTaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TaskInfoRepository extends JpaRepository<TbTaskInfo, String>, QuerydslPredicateExecutor<TbTaskInfo>, JpaSpecificationExecutor<TbTaskInfo> {


}
