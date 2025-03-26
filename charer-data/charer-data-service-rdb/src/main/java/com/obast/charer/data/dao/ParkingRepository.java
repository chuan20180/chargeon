package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbParking;
import com.obast.charer.model.parking.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ParkingRepository extends JpaRepository<TbParking, String>, QuerydslPredicateExecutor<TbParking>, JpaSpecificationExecutor<TbParking> {
    List<Parking> findByStationId(String stationId);
}


