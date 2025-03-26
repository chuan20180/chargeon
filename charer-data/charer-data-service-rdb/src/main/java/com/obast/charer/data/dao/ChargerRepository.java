package com.obast.charer.data.dao;

import com.obast.charer.data.model.device.TbCharger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩DAO接口
 */
public interface ChargerRepository extends JpaRepository<TbCharger, String>, QuerydslPredicateExecutor<TbCharger>, JpaSpecificationExecutor<TbCharger> {

    Optional<TbCharger> findByDn(String dn);

    List<TbCharger> findAllByStationId(String stationId);

}
