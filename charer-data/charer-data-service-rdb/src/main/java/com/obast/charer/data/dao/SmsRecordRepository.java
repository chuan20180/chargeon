package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbSmsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SmsRecordRepository extends JpaRepository<TbSmsRecord, String>, QuerydslPredicateExecutor<TbSmsRecord>, JpaSpecificationExecutor<TbSmsRecord> {

}
