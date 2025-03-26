package com.obast.charer.data.dao;

import com.obast.charer.data.model.device.TbPmsc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩DAO接口
 */
public interface PmscRepository extends JpaRepository<TbPmsc, String>, QuerydslPredicateExecutor<TbPmsc>, JpaSpecificationExecutor<TbPmsc> {

    Optional<TbPmsc> findByDn(String dn);

}
