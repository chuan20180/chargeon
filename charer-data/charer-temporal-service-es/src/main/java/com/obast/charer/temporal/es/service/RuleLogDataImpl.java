package com.obast.charer.temporal.es.service;

import com.obast.charer.temporal.es.document.DocRuleLog;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.model.rule.RuleLog;
import com.obast.charer.temporal.IRuleLogData;
import com.obast.charer.temporal.es.dao.RuleLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RuleLogDataImpl implements IRuleLogData {

    @Autowired
    private RuleLogRepository ruleLogRepository;

    @Override
    public void deleteByRuleId(String ruleId) {
        ruleLogRepository.deleteByRuleId(ruleId);
    }

    @Override
    public Paging<RuleLog> findByRuleId(String ruleId, int page, int size) {
        Page<DocRuleLog> paged = ruleLogRepository.findByRuleIdOrderByLogAtDesc(ruleId, Pageable.ofSize(size).withPage(page - 1));
        return new Paging<>(paged.getTotalElements(),
                paged.getContent().stream().map(o -> MapstructUtils.convert(o, RuleLog.class))
                        .collect(Collectors.toList()));
    }

    @Override
    public void add(RuleLog log) {
        ruleLogRepository.save(MapstructUtils.convert(log, DocRuleLog.class));
    }
}
