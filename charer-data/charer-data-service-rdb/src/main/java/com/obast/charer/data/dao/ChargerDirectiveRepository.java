package com.obast.charer.data.dao;

import com.obast.charer.data.model.device.TbChargerDirective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChargerDirectiveRepository extends JpaRepository<TbChargerDirective, String>, QuerydslPredicateExecutor<TbChargerDirective>, JpaSpecificationExecutor<TbChargerDirective> {

}
