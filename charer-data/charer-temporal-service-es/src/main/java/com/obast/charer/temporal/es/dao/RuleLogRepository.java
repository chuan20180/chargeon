package com.obast.charer.temporal.es.dao;

import com.obast.charer.temporal.es.document.DocRuleLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RuleLogRepository extends ElasticsearchRepository<DocRuleLog, String> {

    void deleteByRuleId(String ruleId);

    Page<DocRuleLog> findByRuleIdOrderByLogAtDesc(String ruleId, Pageable pageable);

}
