package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StorageRepository extends JpaRepository<TbStorage, String>, QuerydslPredicateExecutor<TbStorage>, JpaSpecificationExecutor<TbStorage> {

}
