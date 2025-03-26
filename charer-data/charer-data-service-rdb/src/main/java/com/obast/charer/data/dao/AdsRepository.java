package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbAds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AdsRepository extends JpaRepository<TbAds, String>, QuerydslPredicateExecutor<TbAds>, JpaSpecificationExecutor<TbAds> {

}
