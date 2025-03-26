package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbAlertRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AlertRecordRepository extends JpaRepository<TbAlertRecord, String>, QuerydslPredicateExecutor<TbAlertRecord>, JpaSpecificationExecutor<TbAlertRecord> {

}
