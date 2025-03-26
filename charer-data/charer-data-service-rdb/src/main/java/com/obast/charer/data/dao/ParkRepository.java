package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbPark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ParkRepository extends JpaRepository<TbPark, String>, QuerydslPredicateExecutor<TbPark>, JpaSpecificationExecutor<TbPark> {

}
