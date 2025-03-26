package com.obast.charer.data.dao;

import com.obast.charer.data.model.device.TbDcam;
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
public interface DcamRepository extends JpaRepository<TbDcam, String>, QuerydslPredicateExecutor<TbDcam>, JpaSpecificationExecutor<TbDcam> {

    Optional<TbDcam> findByDn(String dn);

}
