package com.obast.charer.data.dao;

import com.obast.charer.data.model.device.TbCamera;
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
public interface CameraRepository extends JpaRepository<TbCamera, String>, QuerydslPredicateExecutor<TbCamera>, JpaSpecificationExecutor<TbCamera> {

    Optional<TbCamera> findByDn(String dn);

}
