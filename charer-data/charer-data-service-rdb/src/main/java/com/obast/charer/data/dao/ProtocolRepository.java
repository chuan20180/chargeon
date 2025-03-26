package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProtocolRepository extends JpaRepository<TbProtocol, String>, QuerydslPredicateExecutor<TbProtocol>, JpaSpecificationExecutor<TbProtocol> {
    TbProtocol findByProtocolKey(String productKey);

}
