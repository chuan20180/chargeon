package com.obast.charer.data.dao;

import com.obast.charer.data.model.device.TbChargerGun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ChargerGunRepository extends JpaRepository<TbChargerGun, String>, QuerydslPredicateExecutor<TbChargerGun>, JpaSpecificationExecutor<TbChargerGun> {

    List<TbChargerGun> findByChargerId(String chargerId);

}
