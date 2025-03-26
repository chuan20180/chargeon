package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NoticeRepository extends JpaRepository<TbNotice, String>, QuerydslPredicateExecutor<TbNotice>, JpaSpecificationExecutor<TbNotice> {

}
