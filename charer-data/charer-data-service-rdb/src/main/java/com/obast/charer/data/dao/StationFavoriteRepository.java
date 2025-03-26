package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbStationFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StationFavoriteRepository extends JpaRepository<TbStationFavorite, String>, QuerydslPredicateExecutor<TbStationFavorite>, JpaSpecificationExecutor<TbStationFavorite> {

}
